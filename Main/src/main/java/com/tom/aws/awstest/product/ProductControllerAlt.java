package com.tom.aws.awstest.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductControllerAlt {

	private final ProductService service;

	@GetMapping("/get/product")
	public ResponseEntity<ProductResponse> getProductName(@RequestParam("name") @NotBlank(message = "O nome não pode estar em branco") @Valid String request) {
		var response = service.findProductName(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/activate")
	public ResponseEntity<Void> activateProduct(@RequestParam("name") @NotBlank(message = "O nome não pode estar em branco") @Valid String request) {
		service.activateProduct(request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/generate")
	public ResponseEntity<String> generateProducts(@RequestParam int quantity) {
		service.generateProducts(quantity);
		return ResponseEntity.status(HttpStatus.CREATED).body("Generated");
	}
	
}
