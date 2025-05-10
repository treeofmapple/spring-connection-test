package com.tom.aws.awstest.image;

public record ImageTagResponse(
		
	String imageName,
	String tagKey,
	String tagValue
	
) {
	
}
