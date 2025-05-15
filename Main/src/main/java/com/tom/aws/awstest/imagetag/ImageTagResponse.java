package com.tom.aws.awstest.imagetag;

public record ImageTagResponse(
		
		String imageName,
		Long imageSize,
		String category,
		String subCategory
		
) {

}
