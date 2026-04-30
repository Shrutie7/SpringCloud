package com.shrucode.payment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//In Spring Boot 3 + Spring Cloud 2023,
//you DO NOT need @EnableEurekaClient anymore -Eureka client will auto-register automatically
// in localhost:8761 you will see payment-service added as instance registered in eureka
@EnableFeignClients
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
