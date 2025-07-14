package com.tom.aws.awstest.image;

import java.util.List;

public record PageImageResponse(
		
		List<ImageResponse> content,
		int page,
		int size,
		long totalPages,
		long totalElements
		
		) {

}
