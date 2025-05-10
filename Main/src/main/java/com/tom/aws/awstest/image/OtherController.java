package com.tom.aws.awstest.image;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
public class OtherController {

	private final ImageService service;
	
	@PostMapping(value = "/upload/multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ImageResponse>> uploadMultiple(@RequestParam MultipartFile[] files) {
		var response = service.uploadMultiple(files);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping(value = "/download/multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<byte[]>> downloadMultiple(@RequestBody List<String> names) {
	    byte[][] files = service.downloadMultiple(names);
	    return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(files));
	}

	@GetMapping(value = "/tag/get", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllTag() {
		
	    return ResponseEntity.status(HttpStatus.OK).body();
	}
	
	@GetMapping(value = "/tag/search", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchTag() {
		
	    return ResponseEntity.status(HttpStatus.OK).body();
	}
	
	
	
	@PostMapping(value = "/tag/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTag() {
		
	    return ResponseEntity.status(HttpStatus.OK).body();
	}
	
	@DeleteMapping(value = "/tag/remove")
	public ResponseEntity<?> removeTag() {
		
	   return ResponseEntity.status(HttpStatus.OK).body();
	}
	
	@DeleteMapping(value = "/tag/delete")
	public ResponseEntity<?> deleteTag() {
	
		return ResponseEntity.status(HttpStatus.OK).body();
	}
}
