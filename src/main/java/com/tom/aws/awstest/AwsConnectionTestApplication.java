package com.tom.aws.awstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AwsConnectionTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsConnectionTestApplication.class, args);
	}

}
