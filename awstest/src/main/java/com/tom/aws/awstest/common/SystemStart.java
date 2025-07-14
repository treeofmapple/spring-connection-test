package com.tom.aws.awstest.common;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.s3.S3Client;

@Log4j2
@Component
@RequiredArgsConstructor
public class SystemStart {

    private final AwsProperties properties;
	private final S3Client s3Client;
	
	@Getter
	private boolean s3Connected = false;
	
	@PostConstruct
	public void verifyS3Connection() {
	    try {
	        s3Client.getBucketLocation(b -> b.bucket(properties.getBucket()));
	        log.info("Connection successful on bucket: {}", properties.getBucket());
	        s3Connected = true;
	    } catch (Exception e) {
	        log.error("S3 connection failed: {}", e.getMessage(), e);
	        s3Connected = false;
	    }
	}
	
}