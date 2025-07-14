package com.tom.aws.awstest.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tom.aws.awstest.common.AwsProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AccelerateConfiguration;
import software.amazon.awssdk.services.s3.model.BucketAccelerateStatus;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.PutBucketAccelerateConfigurationRequest;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.VersioningConfiguration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class AwsStorageConfig {

    private final AwsProperties properties;
    
    @Getter
    S3Client s3Client;

    @Bean
    S3Client s3Client() {
        return S3Client.builder()
        		.endpointOverride(URI.create(properties.getEndpoint()))
                .region(Region.of(properties.getRegion()))
                //.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .credentialsProvider(StaticCredentialsProvider.create(
                		AwsBasicCredentials.create(properties.getAccessKeyId(), properties.getSecretAccessKey())))
                .build();

    }
        /*
        
        if(properties.isAccelerateEnabled()) {
        	enableAccelerateMode();
        }
        enableVersioning();
        
        */
    
	@SuppressWarnings("unused")
	private void enableAccelerateMode() {
        try {
			s3Client.putBucketAccelerateConfiguration(
			        PutBucketAccelerateConfigurationRequest.builder()
			                .bucket(properties.getBucket())
			                .accelerateConfiguration(
			                        AccelerateConfiguration.builder()
			                                .status(BucketAccelerateStatus.ENABLED)
			                                .build())
			                .build());
		} catch (S3Exception e) {
			log.error("Failed to enable aceleration: {}", e.awsErrorDetails().errorMessage());
		}
    }

    @SuppressWarnings("unused")
	private void enableVersioning() {
        try {
			s3Client.putBucketVersioning(
			        PutBucketVersioningRequest.builder()
			                .bucket(properties.getBucket())
			                .versioningConfiguration(
			                        VersioningConfiguration.builder()
			                                .status(BucketVersioningStatus.ENABLED)
			                                .build())
			                .build());
		} catch (S3Exception e) {
			log.error("Failed to enable bucket versioning: {}", e.awsErrorDetails().errorMessage());
		}
    }
	

    public String getBucketName() {
        return properties.getBucket();
    }
}
