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
