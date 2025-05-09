package com.tom.aws.awstest.product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductResponse>> getAllProduct() {
		var response = service.findAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponse> getProductId(@RequestParam Long id) {
		var response = service.findProductId(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/create", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
		var response = service.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> editProduct(@RequestBody @Valid ProductRequest request) {
		var response = service.updateProduct(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteProduct(@RequestParam("name") @NotBlank(message = "O nome não pode estar em branco") String request) {
		var response = service.deleteProduct(request);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}

}
