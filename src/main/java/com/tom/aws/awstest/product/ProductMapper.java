package com.tom.aws.awstest.product;

import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

	public Product toProduct(ProductRequest request) {
		if (request == null) {
			return null;
		}

		return Product.builder().name(request.name()).quantity(request.quantity()).price(request.price())
				.manufacturer(request.manufacturer()).build();

	}

	public ProductResponse fromProduct(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getQuantity(), product.getPrice(),
				product.getManufacturer(), product.isActive(), product.getCreatedAt(), product.getUpdatedAt());
	}

}
