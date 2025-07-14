package com.tom.aws.awstest.common;

import org.springframework.stereotype.Component;

import com.tom.aws.awstest.config.AwsStorageConfig;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class SystemStart {

    private final AwsProperties properties;
	private final AwsStorageConfig awsConfig;
	
	@Getter
	private boolean s3Connected = false;
	
	@PostConstruct
	public void verifyS3Connection() {
	    try {
	        awsConfig.getS3Client().getBucketLocation(b -> b.bucket(properties.getBucket()));
	        log.info("Connection successful on bucket: {}", properties.getBucket());
	        s3Connected = true;
	    } catch (Exception e) {
	        log.error("S3 connection failed: {}", e.getMessage(), e);
	        s3Connected = false;
	    }
	}
	
}