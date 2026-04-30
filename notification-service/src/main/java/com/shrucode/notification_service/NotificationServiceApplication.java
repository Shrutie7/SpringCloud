package com.shrucode.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//In Spring Boot 3 + Spring Cloud 2023,
//you DO NOT need @EnableEurekaClient anymore -Eureka client will auto-register automatically
// in localhost:8761 you will see notification-service added as instance registered in eureka

public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
