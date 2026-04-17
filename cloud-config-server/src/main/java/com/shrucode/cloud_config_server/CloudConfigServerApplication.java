package com.shrucode.cloud_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudConfigServerApplication.class, args);
	}

}

//CONFIG SERVER :
//purpose of adding a spring cloud config in microservice architecture is storing and serving distributed configuration across multiple applications
//if u have common prop reqd in each and every microservice rather than hardcode those configuration in each & every microservice keep it in some central place whoever microservice need to access that can get it from that central place
//create a git repo in that repo write all common prop in application.yml file &create a spring boot application that is SPRING CLOUD CONFIG SERVER & CONFIG SERVER WILL READ THOSE PROPERTIES FROM THIS GIT REPO
//WHOEVER MICROSERVICE NEED TO ACCESS THOSE PROP THEY CAN DIRECTLY TALK TO THIS CONFIG SERVER . SO CONGIF SERVER WILL ACT AS CENTRAL PLACE WHERE WE CAN STORE ALL THE COMMON PROP
//IN ORDER SERVICE APPLICATION.YML U SEE eureka client configuration same in payment service inventory service cloud gateway so this piece of code we can keep in central place so these 4 microservice can directly talk to config server & fetch it
//in order service service class -> it is doing rest api call to payment service but if someone from payment service change their url again we need to configure code & change the url &rebuid repackage so to avoid that we can add this url in spring cloud config server no need to hardcode any url in our application so in future if their is changes in url we need to just update url in spring cloud config server bcoz our microservice will talk to spring cloud congif server & get updated prop

//add dependency in spring cloud config server application -> config server , eureka client (register as eureka client)