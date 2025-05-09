package com.tom.aws.awstest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AccelerateConfiguration;
import software.amazon.awssdk.services.s3.model.BucketAccelerateStatus;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.PutBucketAccelerateConfigurationRequest;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.VersioningConfiguration;

@Configuration
@RequiredArgsConstructor
public class AwsStorageConfig {

	@Value("${cloud.aws.region}")
	private final String awsRegion;
	
	@Bean
	public S3Client s3Client() {
		return S3Client.builder()
				.region(Region.of(awsRegion))
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.build();
	}
	
	public void accelerateSpeed() {
		awsConfig.s3Client()
				.putBucketAccelerateConfiguration(
				PutBucketAccelerateConfigurationRequest.builder()
			    .bucket(bucketName)
			    .accelerateConfiguration(
			    		AccelerateConfiguration.builder()
			        .status(BucketAccelerateStatus.ENABLED)
			        .build())
			    .build());

	}
	
	public void versioning() {
		awsConfig.s3Client()
				.putBucketVersioning(
				PutBucketVersioningRequest.builder()
			    .bucket(bucketName)
			    .versioningConfiguration(
	    		VersioningConfiguration.builder()
		        .status(
        		BucketVersioningStatus.ENABLED)
			        .build())
			    .build());

	}
	
}
