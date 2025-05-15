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
	ImageResponse fromImage(Image image);
	
}
