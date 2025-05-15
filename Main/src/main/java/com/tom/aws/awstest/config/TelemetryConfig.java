package com.tom.aws.awstest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

@Configuration
@ConditionalOnProperty(name = "tracing.enabled", havingValue = "true", matchIfMissing = false)
public class TelemetryConfig {

	@Bean
	OtlpGrpcSpanExporter otlpGrpcSpanExporter(@Value("${tracing.url}") String url) {
		return OtlpGrpcSpanExporter.builder().setEndpoint(url).build(); 
	}
	
}
