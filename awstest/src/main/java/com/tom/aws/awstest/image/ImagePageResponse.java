package com.tom.aws.awstest.image;

import java.util.List;

public record ImagePageResponse(

	List<Image> content,
	int page,
	int size,
	long totalPages
		
) {

}
