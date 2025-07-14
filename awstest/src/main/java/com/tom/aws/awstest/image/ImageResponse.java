package com.tom.aws.awstest.image;

import java.time.ZonedDateTime;

public record ImageResponse(
		
		long id,
		String name,
	    String objectUrl,
	    String contentType,
	    long size,
	    ZonedDateTime createdAt,
	    ZonedDateTime updatedAt
	    
		) {

}
