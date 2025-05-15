package com.tom.aws.awstest.imagetag;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageTagMapper {

	ImageTagMapper INSTANCE = Mappers.getMapper(ImageTagMapper.class);
	
	
	
	
}
