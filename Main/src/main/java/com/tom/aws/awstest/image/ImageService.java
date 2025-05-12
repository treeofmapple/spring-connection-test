package com.tom.aws.awstest.image;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tom.aws.awstest.common.AwsFunctions;
import com.tom.aws.awstest.common.DataMerger;
import com.tom.aws.awstest.common.ServiceLogger;
import com.tom.aws.awstest.common.SystemUtils;
import com.tom.aws.awstest.exception.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectTaggingResponse;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final AwsFunctions functions;
	private final ImageRepository repository;
	private final ImageMapper mapper;
	private final SystemUtils utils;
	private final DataMerger merger;

	public List<ImageResponse> searchAllObjects() {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for all images", userIp);
		List<Image> images = repository.findAll();
		if(images.isEmpty()) {
			ServiceLogger.warn("No products found");
			throw new NotFoundException("No products found in the database");
		}
		return images.stream().map(mapper::fromImage).collect(Collectors.toList());
	}
	
	public ImageResponse searchObjectByName(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for image by name: {}", userIp, name);
		return repository.findByNameContainingIgnoreCase(name)
				.map(mapper::fromImage)
				.orElseThrow(() -> {
			String message = String.format("Product with id: %s was not found", name);
			ServiceLogger.error(message);
			return new NotFoundException(message);
		});
	}
	
	@Transactional
	public ImageResponse uploadObject(MultipartFile file) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is uploading an image", userIp);
		String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
	    
		functions.putObject(key, file);
		
		String s3Url = functions.buildS3Url(key);
		
		var image = new Image();
	    merger.mergeData(image, file.getOriginalFilename(), key, s3Url, file.getContentType(), file.getSize());
	    repository.save(image);
	    return mapper.fromImage(image);
	}
	
	@Transactional
	public byte[] downloadObject(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is downloading image with name: {}", userIp, name);
		Image images = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
            String message = String.format("Image with name %s not found", name);
            ServiceLogger.error(message);
            return new NotFoundException(message);
		});
		
		return functions.objectAsBytes(images);
	}
	
	@Transactional
	public List<ImageResponse> uploadMultiple(MultipartFile[] files) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is uploading an image", userIp);
	    List<ImageResponse> responses = new ArrayList<>();

	    for (MultipartFile file : files) {
	        String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
	        functions.putObject(key, file);
	        String s3Url = functions.buildS3Url(key);
            var image = new Image();
            merger.mergeData(image, file.getOriginalFilename(), key, s3Url, file.getContentType(), file.getSize());
            repository.save(image);

            responses.add(mapper.fromImage(image));

	    }

	    return responses;
	}
	
	@Transactional
	public byte[][] downloadMultiple(List<String> names) {
	    String userIp = utils.getUserIp();
	    ServiceLogger.info("IP {} is downloading multiple images: {}", userIp, names);

	    byte[][] result = new byte[names.size()][];

	    for (int i = 0; i < names.size(); i++) {
	        String name = names.get(i);
	        Image images = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
	            String message = String.format("Image with name %s not found", name);
	            ServiceLogger.error(message);
	            return new NotFoundException(message);
	        });

	        result[i] = functions.objectAsBytes(images);
	    }

	    return result;
	}
	
	@Transactional
	public String deleteObject(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for all images", userIp);
		
		Image images = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
            String message = String.format("Image with name %s not found", name);
            ServiceLogger.error(message);
            return new NotFoundException(message);
		});
		
		functions.deleteObject(images);
        repository.delete(images);
        ServiceLogger.info("Successfully deleted image with name: {}", name);
        return "Image deleted successfully";
	}
	
	public String renameObject(String name , String rename) {
		
		ServiceLogger.info("Successfully renamed image with name: {} to: {}", name, rename);
		
		
	    image.setObjectKey(newKey);
	    image.setObjectUrl("https://" + awsConfig.getBucketName() + ".s3.amazonaws.com/" + newKey);
	    image.setName(newName);
	    repository.save(image);
		
		return "Image renamed successfully";
	}
	
	
	@Transactional
	public ImageTagResponse addTag(String name, String tagOption, String value) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is adding a new tag to the {} images", userIp, name);
		
	    Image images = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
	        String message = String.format("Image with name %s not found", name);
	        ServiceLogger.error(message);
	        return new NotFoundException(message);
	    });
		
	    functions.addTags(images, tagOption, value);
	    
	    return mapper.fromImageTag(images);
	}
	
	@Transactional
	public List<ImageTagResponse> searchTags(String tagName) {
	    String userIp = utils.getUserIp();
	    ServiceLogger.info("IP {} is searching for images with tag: {}", userIp, tagName);

	    List<ImageResponse> matchingImages = new ArrayList<>();

	    for (Image image : repository.findAll()) {
	        GetObjectTaggingResponse tags = functions.getAllTags(image);
	        boolean hasTag = tags.tagSet().stream()
	            .anyMatch(tag -> tag.key().equalsIgnoreCase(tagName));

	        if (hasTag) {
	            matchingImages.add(mapper.fromImage(image));
	        }
	    }

	    if (matchingImages.isEmpty()) {
	        String message = String.format("No image found with tag: %s", tagName);
	        ServiceLogger.warn(message);
	        throw new NotFoundException(message);
	    }

	    return matchingImages;
	}
	
	@Transactional
	public List<ItemTagResponse> getAllTags() {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is getting all the tags", userIp);
		
	    List<Image> images = repository.findAll();
	    if (images.isEmpty()) {
	        ServiceLogger.warn("No images found for tag retrieval");
	        throw new NotFoundException("No images found in the database");
	    }
		
		for(Image image : images) {
			var response = functions.getAllTags(image);
			
			
		}
		
		return imageTags;
	}

	public ImageTagResponse removeTag() {
		
	}
	
	public ItemTagResponse deleteTag() {
		
	}
	
	
	/*
	
	public byte[] encryptData(byte[] data, SecretKey key) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    return cipher.doFinal(data);
	}
	
	public void uploadEncrypted(String bucket, String keys, byte[] data, SecretKey secretKey) throws Exception {
	    byte[] encrypted = encryptData(data, secretKey);

	    awsConfig.getS3Client()
	    		.putObject(PutObjectRequest.builder()
	            .bucket(bucket)
	            .key(keys)
	            .contentType("application/octet-stream")
	            .build(),
	        RequestBody.fromBytes(encrypted));
	}
	
	public void moveRename() {
		awsConfig.s3Client()
				.copyObject(
				CopyObjectRequest.builder()
			    .sourceBucket(sourceBucket)
			    .sourceKey(sourceKey)
			    .destinationBucket(destBucket)
			    .destinationKey(destKey)
			    .build());

		awsConfig.s3Client()
				.deleteObject(
				DeleteObjectRequest.builder()
			    .bucket(sourceBucket)
			    .key(sourceKey)
			    .build());

	}
	
	*/
	
	// compressed data -- Later
	
}
