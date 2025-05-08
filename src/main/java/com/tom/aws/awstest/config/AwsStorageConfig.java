package com.tom.aws.awstest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

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
	
}
