package com.tom.aws.awstest.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tom.aws.awstest.common.ServiceLogger;
import com.tom.aws.awstest.common.SystemUtils;
import com.tom.aws.awstest.exception.BadRequestException;
import com.tom.aws.awstest.exception.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;
	private final ProductMapper mapper;
	private final SystemUtils utils;
	
    public ProductResponse findProductId(Long productId) {
        ServiceLogger.info("User is finding product with id: {}", productId);
        return repository.findById(productId)
                .map(mapper::fromProduct)
                .orElseThrow(() -> {
                    String message = String.format("Product with id: %s was not found", productId);
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
    }

    public ProductResponse findProductName(NameRequest request) {
        ServiceLogger.info("User is finding product with name: {}", request.name());
        return repository.findByName(request.name())
                .map(mapper::fromProduct)
                .orElseThrow(() -> {
                    String message = String.format("Product with name: %s was not found", request.name());
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
    }

    public List<ProductResponse> findAllProducts() {
        ServiceLogger.info("User is fetching all products");
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            ServiceLogger.warn("No products found");
            throw new NotFoundException("No products found in the database.");
        }
        return products.stream().map(mapper::fromProduct).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        ServiceLogger.info("User is creating a new product: {}", request.name());
        if (repository.existsByName(request.name())) {
            String message = "Product with name " + request.name() + " already exists.";
            ServiceLogger.warn(message);
            throw new BadRequestException(message);
        }
        
        var product = repository.save(mapper.toProduct(request));
        var response = mapper.fromProduct(product);
        
        ServiceLogger.info("Product created successfully with id: {}", product.getId());
        return response;
    }

    @Transactional
    public void updateProduct(ProductRequest request) {
        ServiceLogger.info("User is updating product: {}", request.name());
        var product = repository.findByName(request.name())
                .orElseThrow(() -> {
                    String message = String.format("Product with name %s was not found", request.name());
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
        
        utils.mergeData(product, request);
        repository.save(product);
        
        ServiceLogger.info("Product updated successfully with id: {}", product.getId());
    }

    @Transactional
    public void deleteProduct(NameRequest request) {
        ServiceLogger.info("User is deleting product: {}", request.name());
        if (!repository.existsByName(request.name())) {
            String message = String.format("Product with name %s not found for deletion", request.name());
            ServiceLogger.error(message);
            throw new NotFoundException(message);
        }
        repository.deleteByName(request.name());
        
        ServiceLogger.info("Product deleted successfully: {}", request.name());
    }

    @Transactional
    public void activateProduct(NameRequest request) {
        ServiceLogger.info("User is activating product: {}", request.name());
        var product = repository.findByName(request.name())
                .orElseThrow(() -> {
                    String message = String.format("Product with name %s not found for activation", request.name());
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
        
        if (!product.isActive()) {
            product.setActive(true);
            repository.save(product);
            ServiceLogger.info("Product activated successfully: {}", product.getId());
        } else {
            String message = String.format("Product with name %s is already active", request.name());
            ServiceLogger.warn(message);
            throw new BadRequestException(message);
        }
    }


}
