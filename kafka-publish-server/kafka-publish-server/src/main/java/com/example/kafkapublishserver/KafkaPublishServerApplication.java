package com.example.kafkapublishserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KafkaPublishServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPublishServerApplication.class, args);
	}

}
