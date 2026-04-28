1. ELK stack:

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
PUT inventory-index
{
"settings": {
  "index":{
  "number_of_shards": 1,
  "number_of_replicas": 1
  }
}
}
//index is now created in ELASTIC SEARCH
//once index is created then add document in that index using post query
// --> (POST /inventory-index/default/ this is old syntax )_doc is the document type (types like default are removed).
POST inventory-index/_doc
{
"name": "event processing",
		"instructor": {
		  "firstName": "John",
     	"lastName": "Doe"
		}
}
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



2. Sleuth & zipkin 
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
//SLEUTH IS OUTDATED IN SPRING 3.XX USE MICROMETER TRACING

3. Resilience4j CircuitBreaker
//Resilience4j is alternative of hysterix which helps to manage fault tolerance in microservice & MORE //features & models of Resilience4j
//1. CIRCUIT BREAKER (FAULT TOLERANCE)
//2. RATE LIMITER (BLOCK TOO FREQUENT REQUESTS)
//3. TIME LIMITER (SET A TIME LIMIT WHEN CALLING REMOTE OPERATION)
//4. RETRY MECHANISM (AUTOMATICALLY RETRY A FAILED REMOTE OPERATION)
//5. BULKHEAD (AVOID TOO MANY CONCURRENT REQUESTS)
//6. CACHE (STORE RESULTS OF COSTLY REMOTE OPERATIONS)

// when we have microservice that communicate w each other there is a possibility that 1 service is unavailable/ unable to respond
//user-service•------------500 internal service----•-> catalog service
//                                                             |
//                                                        discount service


//Rather than stop processing request we will set a threshold we will wait for few more calls if failure rate exceed that threshold stop calling the microservice

// 3 states in circuit breaker ->1. Closed 2. open 3. half open
//by default status closed means inventory service can call order service both service up & running
// threshold = 50% -> means 50% of calls from inventory service to order service will fail immediately trip will happen and status will be changed from close to open
// inventory service - - - - - (5 calls) - order service (out of 5, 3 calls failed 2 exceed) failure rate exceed the threshold so circuit breaker status change to open
//in open state cb wont allow u to call dependent microservices & we have timeout once timeout(say 5s) expire immediately change status to half open
// then in half open state it will allow only few calls to go through & check availablity of order service
// if it fails again then status is open state if order service is back online then status changed to closed again

//Gateway is reactive (WebFlux)
//It integrates with Spring Cloud Gateway route filters
//Uses Resilience4j under the hood
//No AOP needed at Gateway Level

//add this dependency in order service pom.xml
//<dependency>
//<groupId›org.springframework.boot</groupId>
//<artifactId›spring-boot-starter-actuator</artifactId>
////</dependency>
//<dependency>
//<groupId>io.github.resilience4j</groupId>
//<artifactId»resilience4j-spring-boot3</artifactId>
//</dependency>
//<dependency>
//<groupId>org.springframework.boot</groupId>
//<artifactId>spring-boot-starter-aop</artifactId>
//</dependency>

//actuator dependency -> to find health of microservice
//aop dependency -> to send metrics to actuator to track cb status
//resilience4j dependency-> to implement cb pattern


//in order service
// public static final String ORDER_SERVICE="orderService";
// add annotation @CircuitBreaker(name= ORDER_SERVICE, fallBackMethod="orderFallBack") // give the fallBackmethod which should be called if order service fails
//keep same return type in your fallback method the same as your end point returns ****INTERVIEW QUESTION
// create fallBack method just below that method

//define service configuration for Resilience4j in application.yml file //enable all endpoint of actuator, configure resilience4j related steps
//resilience4j:circuitBreaker: instances: orderService://registerHealthIndicator:true //eventConsumerBufferSize:10 //failureRateThreshold: 50
// (if 50% req fail from order to inventory service change cb status to open)
//minimumNumberOfCalls: 5// automaticTransitionFrom0penToHalf0penEnabled: true //waitDurationIn0penState: 5s
// (after 5s status of cb automatically change from open to half open)
//permittedNumber0fCallsInHalf0penState: 3
//slidingWindowType: COUNT_BASED (time based is also there) //slidingWindowSize: 10

//with this config this will manage state of cb
//run the 3 services hit apis

//go to localhost: yourport/actuator/health
//state -> closed (no failed call so far)
//stop the inventory service u will see failedCall: 1 (didnt exceed threshold 50%) again failed again failed 3rd time status change to open threshold crossed
// if we refresh then status change to half open bcoz
//we cross the 5s. limit so status change to half open (after 3 calls again it will take decision to go to closed or open state stay for 5s only)
//closed, open and half open to closed or open based on service availability

//Circuit breakers do not protect a service from its own failures.
//They protect the service from failures of downstream dependencies.
//If Order Service itself failed that has cb implementation, recovery handled by infrastructure components like API Gateway, Eureka health checks, container restarts, and monitoring.


4. Retry 
//order service -----taking long time ----->inventory service (kafka/db connected)
//taking long to respond bcoz
//1. Lag in kafka
//2. Database running data center may be down
//3. Temporary unavailablity of dependencies
//4. Deployment environment failure

//idea behind retry pattern in case of failure response order service need to reattempt few calls again & again to inventory service in some time interval
//if any of attempt to inventory service back to online otw option to return fallback response to end user
// resilience4j: retry: instances: orderService: maxRetryAttempts: 3(max no of attempt from order service to inventory service in each 5s once) waitDuration:5s

//private int attempt = 1;
//add annotation @Retry(name= ORDER_SERVICE, fallBackMethod="orderFallBack")
// sout("retry method called " + attempt++ + " times" +" at " + new Date())://increasing attempt & printing time stamp
//hit order service api 3 times if inventory service not back to online returns fallback response otw gives correct response from inventory service
//Retry works ONLY when circuit breaker is CLOSED or HALF-OPEN
//"Retry is attempted first for transient failures.
//If failures persist,
//If failures persist, the circuit breaker records them and opens.
//Once the circuit is open, retries are skipped and fallback logic is executed."     

5. CONFIG SERVER :
//purpose of adding a spring cloud config in microservice architecture is storing and serving distributed configuration across multiple applications
//if u have common prop reqd in each and every microservice rather than hardcode those configuration in each & every microservice keep it in some central place whoever microservice need to access that can get it from that central place
//create a git repo in that repo write all common prop in application.yml file &create a spring boot application that is SPRING CLOUD CONFIG SERVER & CONFIG SERVER WILL READ THOSE PROPERTIES FROM THIS GIT REPO
//WHOEVER MICROSERVICE NEED TO ACCESS THOSE PROP THEY CAN DIRECTLY TALK TO THIS CONFIG SERVER . SO CONGIF SERVER WILL ACT AS CENTRAL PLACE WHERE WE CAN STORE ALL THE COMMON PROP
//IN ORDER SERVICE APPLICATION.YML U SEE eureka client configuration same in payment service inventory service cloud gateway so this piece of code we can keep in central place so these 4 microservice can directly talk to config server & fetch it
//in order service service class -> it is doing rest api call to payment service but if someone from payment service change their url again we need to configure code & change the url &rebuid repackage so to avoid that we can add this url in spring cloud config server no need to hardcode any url in our application so in future if their is changes in url we need to just update url in spring cloud config server bcoz our microservice will talk to spring cloud congif server & get updated prop

//add dependency in spring cloud config server application -> config server , eureka client (register as eureka client)


//6.Feign Client
//at top put @FeignClient(name = "INVENTORY-SERVICE")
// Feign = same as Controller, but inside interface
//translate controller APIs into Feign methods.
// do autowire in service we want to use that other service which we made feign client interface 
//    @Autowired
// private InventoryFeignClient inventoryFeignClient;

