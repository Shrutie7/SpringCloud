package com.shrucode.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
//Resilience4j is alternative of hysterix which helps to manage fault tolerance in microservice & MORE //features & models of Resilience4j
//1. CIRCUIT BREAKER (FAULT TOLERANCE)
//2. RATE LIMITER (BLOCK TOO FREQUENT REQUESTS)
//3. TIME LIMITER (SET A TIME LIMIT WHEN CALLING REMOTE OPERATION)
//4. RETRY MECHANISM (AUTOMATICALLY RETRY A FAILED REMOTE OPERATION)
//5. BULKHEAD CAVOID TOO MANY CONCURRENT REQUESTS)
//6. CACHE (STORE RESULTS OF COSTLY REMOTE OPERATIONS)

// when we have microservice that communicate w each other there is a possiblity that 1 service is unavailable/ unable to respond
//user-service•------------500 internal service----•-> catalog service
//                                                             |
//                                                        discount service


//Rather than stop processing request we will set a threshold we will wait for few more calls if failure rate exceed that threshold stop calling the microservice

// 3 states in circuit breaker ->1. Closed 2. open 3. half open
//by default status closed means inventory service can call order service both service up & running
// threshold = 50% -> means 50% of calls from inventoryservice to order service will fail immediately trip will happen and status will be changed from close to open
// inventory service - - - - - (5 calls) - order service (out of 5, 3 calls failed 2 exceed) failure rate exceed the threshold so circuit breaker status change to open
//in open state cb wont allow u to call dependent microservices & we have timeout once timeout(say 5s) expire immediately change status to half open
// then in half open state it will allow only few calls to go through & check availablity of order service
// if it fails again then status is open state if order service is back online then status changed to closed again

//Gateway is reactive (WebFlux)
//It integrates with Spring Cloud Gateway route filters
//Uses Resilience4j under the hood
//No AOP needed at Gateway Level

//actuator dependency -> to find health of microservice
//aop dependency -> to send metrics to actuator to track cb status
//resilience4j dependency-> to implement cb pattern
//add this dependency in order service pom.xml

//