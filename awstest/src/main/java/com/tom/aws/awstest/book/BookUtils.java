package com.tom.aws.awstest.book;

import org.springframework.stereotype.Component;

import com.tom.aws.awstest.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookUtils {

	private final BookRepository repository;
	
	public Book findById(long id) {
		return repository.findById(id).orElseThrow(() -> {
			return new NotFoundException("Book not found with id: " + id);
		});
	}
	
	public void ensureUniqueName(String title) {
		if (repository.existsByTitle(title)) {
			throw new NotFoundException("Name already exists");
		}
	}

}
