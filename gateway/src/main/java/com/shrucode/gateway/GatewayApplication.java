package com.shrucode.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}


//Logs are messages written by an application to explain what it is doing and where it failed

//In microservices,
//logs are scattered across services
//ELK centralises logs by collecting them using "Logstash", storing them in "Elasticsearch" , and visualising them in "Kibana" for easy debugging

//Centralised logging using ELK means all applications send their logs to one central system where logs can be stored,searched and viewed easily.

//ELK -> 3 HELPERS WORKING TOGETHER
//1. logstash - the collector
//2. ElasticSearch - the brain
//3. Kibana - the tv screen

//Spring Boot Microservices (Order, Payment, Inventory, Gateway)
//            ↓ writes logs
//Logback Logs
//            ↓
//Logstash (collect + process logs)
//            ↓ sends logs
//Elasticsearch (store + index logs)
//            ↓ shows logs
//Kibana (visualize + search logs)

// we will create log of each microservice then we will give log to ELK STACK to centralise all logs in 1 place so that whenever developer want to see log of ny microservice he can easily filter it out from ELK stack


//1. GENERATE log file of our microservices:

//in order service we have 1 post method saveOrder just capture request and response make Logger and do log.info so that from kibana console we can easily filter this out based on the service name
//    private Logger log = LoggerFactory.getLogger(OrderService.class); // coming from Slf4j
// log.info("OrderService Request: {}",new ObjectMapper().writeValueAsString(transactionRequest));//to view it in JSON MODE use ObjectMapper().writeValueAsString //throws JsonProcessingException from method signature

//do this in payment service and inventory service also

//to generate log file of it go to application.yml file and add logging file name same for all 3 service we can have different log file also for different microservice but we have same log file to view in kibana console

//restart all 3 services
//install docker desktop check this in cmd then docker --version and docker compose version
//once it comes make a folder called elkstack and create docker-compose.yml and logstash/logstash.conf file
//start docker desktop and open cmd in this elkstack folder and do docker ps means docker is running then do docker compose up
//docker is downloading huge images for logstash kibana and elasticSearch once done
//now all 3 elastic search, kibana and logstash will run
//default port of elastic search = 9200 run it we see a json, localhost: 9200/_cat/indices
//we can create custom index in kibana dashboard we can do rest api call(query) & generate index there itself
//default port of kibana = 5601, create index, in logstash file mention index to see all logs from appl to see in kibana console
//PUT index-name            → create index
//POST index/_doc           → insert data
//GET _cat/indices?v        → list indices
//GET index/_search         → query data
//go to kibana --> Dev tools -> write query to create index ->
//PUT inventory-index
//{
//"settings": {
//"number_of_shards": 1,
//"number_of_replicas": 1
//}
//}
//index is now created in ELASTIC SEARCH
//once index is created then add document in that index using post query
// --> (POST /inventory-index/default/ this is old syntax )_doc is the document type (types like default are removed).
//POST inventory-index/_doc
//{
//"name"; "event processing",
//		"instructor": {
//		"firstname"; "John",
//		"Lastname": "Doe"
//		}
//}
//go to log folder in desktop in microservice u will see the log file all 3 service logs will come in that log file

//now we need to give log file to logstash
//and all the logs logstash will document it & send it to elastic search
//elastic search will document those logs & send it to kibana
//and we can view logs in kibana ui

//*******************************************************************************
// in logstash.conf give path of log file and specify index we created inventory-index and append timestamp to it
//logstash will go to log file & read all log

//run logstash again it will be up & print logs in cmd in logstash console
//go to kibana console -> create index pattern for new index we created & add to this kibana
//management -> Index Patterns -> create index pattern -› name it inventory-index (same as index we created)-›next step -› enable /not time stamp -> create index pattern click

//click on discover icon-›Select inventory-index-> it will redirect to log folder-› filter ur index here
// run all 3 services -> go to logstash cmd -> u see logs of those api u have hit from bruno
//go to kibana ui & refresh u see hit count increase & logs coming which we added in service
// Go to Discover
//Select inventory-index*
//Hit APIs
//Refresh → log count increases & we can view logs in table format/json format

// we can create diff index for each microservice / diff log file for each microservice

//Microservice
//  | (logs)
// Log File
//  |
// Logstash
//  | (documents)
// Elasticsearch
//  | (query only not push)
// Kibana

//Logstash Reads logs + sends documents
//Elasticsearch Stores & indexes documents
//Kibana Only queries & visualizes data

//how to use spring cloud sleuth & zipkin to perform distributed log tracing ?
//Sleuth adds traceld to logs
//Sleuth → adds IDs
//Zipkin → stores spans
//ELK → stores logs

//use case of sleuth & zipkin
// in typical microservice architecture we have seprate small microservie deployed seperately& they often need to communicate
//Inventory service-›order service -› payment service -› Googlepay/paytm service -›bank service -›user detail service -›notification service
//during intermicroservice communication issue occured due to exception occured in 1 of service / either 1 service goes down which service
// is having problem with circuitbreaker&fallback controller identify service getting down/problem
//say bankservie has problem it runs on 2 instance 9090 & 9091 difficult to identify which instance goes down // --› track entire req chain --›not possible to each & every microservice to check log →> so we have sleuth & zipkin
//per each req sleuth generate meta data
// 4 elements ->
// SERVICE NAME, TRACEID(UNIQUE ID REMAIN SAME THROUGHOUT MICROSERVICE FOR PARTICULAR REQUEST SAME ACROSS MICROSERVICE), SPAN ID(UNIQUE ID PER MICROSERVICE), EXPORT FLAG

//1.START ZIPKIN SERVER
//2. GO TO zipkin.io & we can download from docker /from mvn repo
//3. run this zipkin server started on 9411 default -› zipkin dashboard
//4.register 3 microservice in zipkin server
//5. go to 3 microservice & add dependency pom.xml-> sleuth & zipkin no need to add in gateway/service registery bcoz they r component of microservice
//6. in each microservice tell where zipkin server is up & running so req chain can be pushed to zipkin // add in yml file of each microservice

//zipkin:
//base-url: http://localhost:9411

//7. restart 3 services & check eureka server 8761 & hit api & in terminal of order service u see 4 components SERVICE NAME, TRACEID, SPAN ID, EXPORT FLAG in payment service terminal
//8.check 9411 zipkin ui u see the 3 services there click on find traces button we see no of spans & we see req flow & entire history we can get for a service

//We add traceld extraction in Logstash so that logs from different microservices can be grouped together by request and searchable in Kibana.

//Sleuth adds traceld
//Logstash extracts traceld
//Elasticsearch stores traceld
//Kibana searches traceld


//add in logstash.conf file
//filter {
//	grok {
//		match =>｛
//          "message" => "\[%(DATA: service},%{DATA:traceId},%{DATA:spanId} ,%{DATA: exportFlag}\]"
//}
//}
//}
//must go INSIDE the filter {} section of logstash.conf,
// after input {} and before output {}.

//In Kibana Discover, select the logs index and search using traceld: "‹value>" to see all logs for a single request across microservices.