package com.tom.aws.awstest.common;
import com.tom.aws.awstest.image.Image;
import com.tom.aws.awstest.image.oldTag.ImageTagOld;
import com.tom.aws.awstest.product.Product;
import com.tom.aws.awstest.product.ProductRequest;

public class DataMerger {

	public void mergeData(Product product, ProductRequest request) {
		product.setName(request.name());
		product.setPrice(request.price());
		product.setQuantity(request.quantity());
		product.setManufacturer(request.manufacturer());
	}
	
	public void mergeData(Image images, String name, String key, String url, String contentType, long size) {
		images.setName(name);
		images.setObjectKey(key);
		images.setObjectUrl(url);
		images.setSize(size);
		images.setContentType(contentType);
	}	
	
	public void mergeData(Image images, String key, String url) {
		images.setObjectKey(key);
		images.setObjectUrl(url);
	}
	
	public void mergeData(ImageTagOld images, Image image, String tagKey, String tagValue) {
		images.setImage(image);
		images.setTagKey(tagKey);
		images.setTagValue(tagValue);
		
	}
	
	public void mergeData(ImageTagOld images, String tagKey, String tagValue) {
		images.setTagKey(tagKey);
		images.setTagValue(tagValue);
		
	}
}
