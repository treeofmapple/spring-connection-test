package com.tom.aws.awstest.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	@Mapping(source = "name", target = "name")
	@Mapping(source = "quantity", target = "quantity")
	@Mapping(source = "price", target = "price")
	@Mapping(source = "manufacturer", target = "manufacturer")
	Product toProduct(ProductRequest request);
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "quantity", target = "quantity")
	@Mapping(source = "price", target = "price")
	@Mapping(source = "manufacturer", target = "manufacturer")
	@Mapping(source = "active", target = "active")
	@Mapping(source = "createdAt", target = "created")
	@Mapping(source = "updatedAt", target = "updated")
	ProductResponse fromProduct(Product product);
	
}
