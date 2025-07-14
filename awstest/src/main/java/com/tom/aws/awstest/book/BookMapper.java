package com.tom.aws.awstest.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "isbn", ignore = true)
	Book build(BookRequest request);
	
	BookResponse toResponse(Book book);
	
	@Mapping(target = "id", ignore = true)
	void updateBookFromRequest(@MappingTarget Book book, UpdateRequest request);
	
	@Mapping(target = "id", ignore = true)
	Book updateBookFromData(@MappingTarget Book book, String isbn, String title, String author, BigDecimal price,
			LocalDate publishedDate, Integer stockQuantity);

	BookDTO toKafka(Book book);
	
	List<BookResponse> toResponseList(List<Book> books);
	
	default BookPageResponse toBookPageResponse(Page<Book> page) {
		if(page == null) {
			return null;
		}
		List<BookResponse> content = toResponseList(page.getContent());
		return new BookPageResponse(content,
				page.getNumber(),
				page.getSize(),
				page.getTotalPages(),
				page.getTotalElements()
			);
	}
	
}
