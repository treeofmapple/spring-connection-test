package com.tom.aws.awstest.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tom.aws.awstest.common.GenerateData;
import com.tom.aws.awstest.common.ServiceLogger;
import com.tom.aws.awstest.common.SystemUtils;
import com.tom.aws.awstest.exception.BadRequestException;
import com.tom.aws.awstest.exception.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private static int QUANTITY = 50;
	private final ProductRepository repository;
	private final ProductMapper mapper;
	private final GenerateData data;
	private final SystemUtils utils;
	
    public ProductResponse findProductId(Long productId) {
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is finding product with id: {}", userIp, productId);
        return repository.findById(productId)
                .map(mapper::fromProduct)
                .orElseThrow(() -> {
                    String message = String.format("Product with id: %s was not found", productId);
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
    }

    public ProductResponse findProductName(String request) {
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is finding product with name: {}", userIp, request);
        return repository.findByName(request)
                .map(mapper::fromProduct)
                .orElseThrow(() -> {
                    String message = String.format("Product with name: %s was not found", request);
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
    }

    public List<ProductResponse> findAllProducts() {
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is fetching all products", userIp);
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            ServiceLogger.warn("No products found");
            throw new NotFoundException("No products found in the database.");
        }
        return products.stream().map(mapper::fromProduct).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is creating a new product: {}", userIp, request.name());
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
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is updating product: {}", userIp, request.name());
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
    public void deleteProduct(String request) {
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is deleting product: {}", userIp, request);
        if (!repository.existsByName(request)) {
            String message = String.format("Product with name %s not found for deletion", request);
            ServiceLogger.error(message);
            throw new NotFoundException(message);
        }
        repository.deleteByName(request);
        
        ServiceLogger.info("Product deleted successfully: {}", request);
    }

    @Transactional
    public void activateProduct(String request) {
    	String userIp = utils.getUserIp();
        ServiceLogger.info("User {} is activating product: {}", userIp, request);
        var product = repository.findByName(request)
                .orElseThrow(() -> {
                    String message = String.format("Product with name %s not found for activation", request);
                    ServiceLogger.error(message);
                    return new NotFoundException(message);
                });
        
        if (!product.isActive()) {
            product.setActive(true);
            repository.save(product);
            ServiceLogger.info("Product activated successfully: {}", product.getId());
        } else {
            String message = String.format("Product with name %s is already active", request);
            ServiceLogger.warn(message);
            throw new BadRequestException(message);
        }
    }

	public void generateProducts() {
    	repository.deleteAll();
		for(int i = 0; i <= QUANTITY; i++) {
			var gen = data.datagen();
			repository.save(gen);
		}
	}

}
