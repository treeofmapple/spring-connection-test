package com.tom.aws.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tom.aws.kafka.book.Book;
import com.tom.aws.kafka.book.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookConsumer {
	
	private final BookRepository bookRepository;

	@KafkaListener(topics = "${spring.kafka.consumer.topic.name}",
			containerFactory = "kafkaListenerContainerFactory")
	public Book recieveBookData(Book book) {
		return bookRepository.save(book);
	}
	
}
