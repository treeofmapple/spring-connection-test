package com.tom.aws.awstest.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsProperties {
 
	private final String endpoint;
	private final String accessKeyId;
	private final String secretAccessKey;
    private final String bucket;
	private final String region;
	private final String ddlAuto;
    private final boolean accelerateEnabled = false;

}
