package com.tom.aws.awstest.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.tom.aws.awstest.product.Product;
import com.tom.aws.awstest.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@Profile("debug")
@RequiredArgsConstructor
public class SystemStarts implements CommandLineRunner {

	private static int QUANTITY = 50;
	
	private final GenerateData data;
	private final ProductRepository repository;
	
    @Override
    public void run(String... args) throws Exception {
    	repository.deleteAll();
		for(int i = 0; i <= QUANTITY; i++) {
			var gen = data.datagen();
			repository.save(gen);
		}
	}
	
    @CachePut(value = "products", key = "#product.id")
    public Product cacheProduct(Product product) {
        return product;
    }
    
}

