package com.tom.aws.awstest.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;
	private final ProductMapper mapper;
	
	public ProductResponse findProductId(Long productId) {
		return repository.findById(productId).map(mapper::fromProduct).orElse(null);
	}

	public ProductResponse findProductName(NameRequest request) {
		return repository.findByName(request.name()).map(mapper::fromProduct).orElse(null);
	}

	public List<ProductResponse> findAllProducts() {
		List<Product> product = repository.findAll();
		if(product.isEmpty()) {
			throw new RuntimeException("");
		}
		return product.stream().map(mapper::fromProduct).collect(Collectors.toList());
	}

	@Transactional
	public ProductResponse createProduct(ProductRequest request) {
		if(repository.existsByName(request.name())) {
			throw new RuntimeException("");
		}
		
		var data = repository.save(mapper.toProduct(request));
		var response = mapper.fromProduct(data);
		
		return response;
	}

	@Transactional
	public void updateProduct(ProductRequest request) {
		var product = repository.findByName(request.name()).orElse(null);
		mergeData(product, request);
		repository.save(product);
	}

	@Transactional
	public void deleteProduct(NameRequest request) {
		if(!repository.existsByName(request.name())) {
			throw new RuntimeException("");
		}
		repository.deleteByName(request.name());
	}

	@Transactional
	public void activateProduct(NameRequest request) {
		var product = repository.findByName(request.name()).orElse(null);
		if(!product.isActive()) {
			product.setActive(true);
		} else {
			throw new RuntimeException("");
		}
		
	}

	private void mergeData(Product product, ProductRequest request) {
		product.setName(request.name());
		product.setPrice(request.price());
		product.setQuantity(request.quantity());
		product.setManufacturer(request.manufacturer());
	}
	
}
