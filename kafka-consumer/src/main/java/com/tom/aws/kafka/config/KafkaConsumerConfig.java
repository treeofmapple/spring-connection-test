package com.tom.aws.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.tom.aws.kafka.book.Book;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Bean
	Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return props;
	}

	@Bean
	ConsumerFactory<String, Book> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(), 
				new StringDeserializer(),
				new JsonDeserializer<>(Book.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, Book> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Book> factory = 
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}
