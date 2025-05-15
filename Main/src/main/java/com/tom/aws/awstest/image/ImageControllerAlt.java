package com.tom.aws.awstest.image;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class ImageControllerAlt {

	private final ImageService service;
	
	@PostMapping(value = "/upload/multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ImageResponse>> uploadMultiple(@RequestParam MultipartFile[] files) {
		var response = service.uploadMultiple(files);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
	@PostMapping(value = "/download/multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<byte[]>> downloadMultiple(@RequestBody List<String> names) {
	    byte[][] files = service.downloadMultiple(names);
	    return ResponseEntity.status(HttpStatus.ACCEPTED).body(Arrays.asList(files));
	}

	/*
	
	@GetMapping(value = "/tag/get", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ItemTagResponse>> getAllTag() {
		var response = service.getAllTags();
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/tag/search", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ImageTagResponse>> searchTag(@RequestParam String name) {
		var response = service.searchTags(name);
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/tag/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ItemTagResponse> createTagKeyValue(@RequestParam("key") String tagKey, @RequestParam("value") String tagValue) {
		var response = service.createTagKeyValue(tagKey, tagValue);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping(value = "/tag/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageTagResponse> addTag(@RequestParam("name") String image, @RequestParam("key") String tagKey, @RequestParam("value") String tagValue) {
		var response = service.addTag(image, tagKey, tagValue);
	    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}
	
	@DeleteMapping(value = "/tag/remove")
	public ResponseEntity<ImageTagResponse> removeTag(@RequestParam("name") String image, @RequestParam("key") String tagKey, @RequestParam("value") String tagValue) {
		var response = service.removeTag(image, tagKey, tagValue);
	   return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
	
	@DeleteMapping(value = "/tag/delete")
	public ResponseEntity<ItemTagResponse> deleteTag(@RequestParam("key") String tagKey, @RequestParam("value") String tagValue) {
		var response = service.deleteTag(tagKey, tagValue);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
	
	*/
}
