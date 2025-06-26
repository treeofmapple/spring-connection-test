package com.tom.aws.awstest.product.service;

import org.springframework.stereotype.Service;

import com.tom.aws.awstest.product.dto.ProductRequest;
import com.tom.aws.awstest.product.model.Product;

@Service
public class ProductUtil {

	public void mergeData(Product product, ProductRequest request) {
		product.setName(request.name());
		product.setPrice(request.price());
		product.setQuantity(request.quantity());
		product.setManufacturer(request.manufacturer());
	}
	
}
