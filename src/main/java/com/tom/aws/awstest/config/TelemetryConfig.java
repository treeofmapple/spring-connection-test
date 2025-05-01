package com.tom.aws.awstest.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

@Configuration
public class TelemetryConfig {

	@Value("${tempo.url}")
	private String url;
	
    @Value("${tempo.username}")
    private String username;

    @Value("${tempo.password}")
    private String password;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Bean
	OpenTelemetry openTelemetry() {
        String basicAuth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        OtlpHttpSpanExporter spanExporter = OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .addHeader("Authorization", "Basic " + basicAuth)
                .build();
        
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(spanExporter).build())
                .setResource(Resource.create(Attributes.of(
                    AttributeKey.stringKey("service.name"), applicationName
                )))
                .build();
        
        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .build();
	}
	
}
