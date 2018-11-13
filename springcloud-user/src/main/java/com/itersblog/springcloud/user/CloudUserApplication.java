package com.itersblog.springcloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(CloudUserApplication.class, args);
	}
}
