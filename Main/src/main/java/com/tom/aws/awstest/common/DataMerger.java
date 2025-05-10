package com.tom.aws.awstest.common;

import com.tom.aws.awstest.image.Image;
import com.tom.aws.awstest.product.Product;
import com.tom.aws.awstest.product.ProductRequest;

public class DataMerger {

	public void mergeData(Product product, ProductRequest request) {
		product.setName(request.name());
		product.setPrice(request.price());
		product.setQuantity(request.quantity());
		product.setManufacturer(request.manufacturer());
	}
	
	public void mergeData(Image image, String name, String key, String s3Url, String contentType, Long size) {
		image.setName(name);
		image.setObjectKey(key);
		image.setObjectUrl(s3Url);
		image.setContentType(contentType);
		image.setSize(size);
	}
	
}
