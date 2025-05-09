package com.tom.aws.awstest.image;

import java.time.LocalDateTime;
import java.util.List;

public record ImageResponse(
		
	String name,
	String contentType,
	String tag,
	Long size,
	List<TagDTO> tags,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
		
) {

	public record TagDTO(String key, String value) {}
	
}
