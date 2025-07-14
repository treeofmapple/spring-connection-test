package com.tom.aws.awstest.image;

public record ImageGenResponse(
		
		byte[] content,
		String fileName,
		String contentType
		
		) {

}
