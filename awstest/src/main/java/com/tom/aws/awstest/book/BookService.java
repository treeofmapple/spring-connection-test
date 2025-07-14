package com.tom.aws.awstest.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tom.aws.awstest.common.SecurityUtils;
import com.tom.aws.awstest.kafka.BookProducer;
import com.tom.aws.awstest.logic.GenData;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookService {

	private final BookMapper mapper;
	private final BookProducer bookProducer;
	private final GenData dataGeneration;
	private final SecurityUtils securityUtils;

	@Transactional
	public BookResponse sendBookRandomToKafka() {
		var book = dataGeneration.processGenerateAnBook();
		bookProducer.sendBook(book);
		return mapper.toResponse(book);
	}

	public void generateBooks(int quantity) {
    	String userIp = securityUtils.getRequestingClientIp();
		log.info("IP {} is generating {}: books", userIp, quantity);
		
		dataGeneration.processGenerateBooks(quantity);
		log.info("Products finished generation");
	}
	
}
