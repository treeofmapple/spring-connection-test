package com.tom.aws.awstest.product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService service;

	@GetMapping("/get")
	public ResponseEntity<List<ProductResponse>> getAllProduct() {
		var response = service.findAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ProductResponse> getProductId(@PathVariable Long id) {
		var response = service.findProductId(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/create")
	public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
		var response = service.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/edit")
	public ResponseEntity<Void> editProduct(@RequestBody @Valid ProductRequest request) {
		service.updateProduct(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteProduct(@RequestParam("name") @Valid String request) {
		service.deleteProduct(request);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
