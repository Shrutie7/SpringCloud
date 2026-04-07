package com.shrucode.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//In Spring Boot 3 + Spring Cloud 2023,
//you DO NOT need @EnableEurekaClient anymore -Eureka client will auto-register automatically
// in localhost:8761 you will see order-service added as instance registered in eureka
public class OrderServiceApplication {

	@Bean
	@LoadBalanced // indicates cient side load balancing removes the exception of unknown host
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
