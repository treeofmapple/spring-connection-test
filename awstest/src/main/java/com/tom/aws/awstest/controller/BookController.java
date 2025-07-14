package com.tom.aws.awstest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tom.aws.awstest.book.BookResponse;
import com.tom.aws.awstest.book.BookService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService service;

	@PostMapping(value = "/data/send")
	public ResponseEntity<BookResponse> sendRandomBookToAws(){
		var response = service.sendBookRandomToKafka();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/data/generate/{quantity}")
	public ResponseEntity<Void> generateSomeBooks(
			@PathVariable int quantity) {
		service.generateBooks(quantity);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
