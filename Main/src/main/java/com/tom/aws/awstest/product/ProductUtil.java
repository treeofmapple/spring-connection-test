package com.tom.aws.awstest.product;

import org.springframework.stereotype.Service;

@Service
public class ProductUtil {

	public void mergeData(Product product, ProductRequest request) {
		product.setName(request.name());
		product.setPrice(request.price());
		product.setQuantity(request.quantity());
		product.setManufacturer(request.manufacturer());
	}
	
}
