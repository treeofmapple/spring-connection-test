package com.tom.aws.kafka.book;

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
	
    @Mapping(source = "createdAt", target = "createdAt") 
    @Mapping(source = "updatedAt", target = "updatedAt") 
	BookResponse toResponse(Book book);
	
	@Mapping(target = "id", ignore = true)
	void updateBookFromRequest(@MappingTarget Book book, UpdateRequest request);
	
	@Mapping(target = "id", ignore = true)
	Book updateBookFromData(@MappingTarget Book book, String isbn, String title, String author, BigDecimal price,
			LocalDate publishedDate, Integer StockQuantity);

	@Mapping(target = "id", ignore = true)
	Book toEntity(BookDTO book);
	
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
