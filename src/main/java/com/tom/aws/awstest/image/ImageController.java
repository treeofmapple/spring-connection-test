package com.tom.aws.awstest.image;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService service;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ImageResponse>> searchAllImage() {
		var response = service.searchAllImages();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageResponse> searchImageByName(@RequestParam("name") String image) {
		var response = service.searchImageByName(image);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/upload",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageResponse> uploadImage(MultipartFile file) {
		var response = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping(value = "/download", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> downloadImage(@RequestParam("name") String images) {
		var response = service.downloadImage(images);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteImage(@RequestParam("name") String images) {
		var response = service.deleteImage(images);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
}
