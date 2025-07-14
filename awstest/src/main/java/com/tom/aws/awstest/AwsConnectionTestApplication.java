package com.tom.aws.awstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.tom.aws.awstest.common.AwsProperties;

@EnableAsync
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConnectionTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsConnectionTestApplication.class, args);
	}

	//TODO: Docker Compose
	//TODO: AWS LAMBDA
	
}
