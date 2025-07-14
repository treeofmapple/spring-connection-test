package com.tom.aws.awstest.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tom.aws.awstest.book.Book;
import com.tom.aws.awstest.book.BookDTO;
import com.tom.aws.awstest.book.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookProducer {

	private final KafkaTemplate<String, BookDTO> kafkaTemplate;
	private final BookMapper mapper;
	
    @Value("${spring.kafka.producer.topic.name}")
    private String topicName;
	
    public void sendBook(Book book) {
    	var bookEntity = mapper.toKafka(book);
        kafkaTemplate.send(topicName, bookEntity);
    }
    
}
