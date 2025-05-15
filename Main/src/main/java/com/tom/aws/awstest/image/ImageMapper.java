package com.tom.aws.awstest.image;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);
	
	// @Mapping(source = "", target = "")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "contentType", target = "contentType")
	@Mapping(source = "size", target = "size")
	@Mapping(source = "createdAt", target = "createdAt")
	@Mapping(source = "updatedAt", target = "updatedAt")
	@Mapping(source = "image.tags", target = "tags")
	ImageResponse fromImage(Image image);
	
	/*
	
	@Mapping(source = "tagKey", target = "tagKey")
	@Mapping(source = "tagValue", target = "tagValue")
	@Mapping(source = "image", target = "image")
	ImageTag buildAttributes(String tagKey, String tagValue, Image image);
	
	@Mapping(source = "image.name", target = "imageName")
	@Mapping(source = "tagKey", target = "tagKey")
	@Mapping(source = "tagValue", target = "tagValue")
	ImageTagResponse fromImageTag(ImageTag imageTag);

	@Mapping(source = "tagKey", target = "tagKey")
	@Mapping(source = "tagValue", target = "tagValue")
	ItemTagResponse fromItemTag(ImageTag imageTag);
	
	
	default ImageResponse.TagDTO map(ImageTag tag) {
	    return new ImageResponse.TagDTO(tag.getTagKey(), tag.getTagValue());
	}
	
	
	*/
}
