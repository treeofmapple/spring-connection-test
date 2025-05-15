package com.tom.aws.awstest.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tom.aws.awstest.config.AwsProperties;
import com.tom.aws.awstest.config.AwsStorageConfig;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SystemStart {

    private final AwsProperties properties;
	private final AwsStorageConfig awsConfig;
	
	@Bean
	String isS3Connected() {
	    try {
	        awsConfig.getS3Client().getBucketLocation(b -> b.bucket(properties.getBucket()));
	        ServiceLogger.info("Connection successfull on bucket:{} ", properties.getBucket());
	        return "";
	    } catch (Exception e) {
	        ServiceLogger.warn("S3 wasn't able to connect", e.getMessage());
	    	return "";
	    }
	}
	
}
