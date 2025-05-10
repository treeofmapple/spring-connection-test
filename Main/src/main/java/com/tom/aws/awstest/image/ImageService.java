package com.tom.aws.awstest.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tom.aws.awstest.common.DataMerger;
import com.tom.aws.awstest.common.ServiceLogger;
import com.tom.aws.awstest.common.SystemUtils;
import com.tom.aws.awstest.config.AwsStorageConfig;
import com.tom.aws.awstest.exception.DataTransferenceException;
import com.tom.aws.awstest.exception.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectTaggingRequest;
import software.amazon.awssdk.services.s3.model.GetObjectTaggingResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectTaggingRequest;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final AwsStorageConfig awsConfig;
	private final ImageRepository repository;
	private final ImageMapper mapper;
	private final SystemUtils utils;
	private final DataMerger merger;

	public List<ImageResponse> searchAllImages() {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for all images", userIp);
		List<Image> images = repository.findAll();
		if(images.isEmpty()) {
			ServiceLogger.warn("No products found");
			throw new NotFoundException("No products found in the database");
		}
		return images.stream().map(mapper::fromImage).collect(Collectors.toList());
	}
	
	public ImageResponse searchImageByName(String name) {
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
	public ImageResponse uploadImage(MultipartFile file) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is uploading an image", userIp);
		try {
			String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
		    awsConfig.getS3Client().putObject(PutObjectRequest.builder()
		        .bucket("your-bucket-name")
		        .key(key)
		        .contentType(file.getContentType())
		        .build(),
		        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
	        );
		    String s3Url = awsConfig.getS3Client()
		    		.utilities()
		    		.getUrl(builder -> builder.bucket(awsConfig.getBucketName()).key(key))
		    		.toExternalForm();
		    var image = new Image();
		    merger.mergeData(image, file.getOriginalFilename(), key, s3Url, file.getContentType(), file.getSize());
		    repository.save(image);
		    return mapper.fromImage(image);
	    } catch(IOException e) {
	    	throw new DataTransferenceException("Error uploading image", e);
	    }
	}
	
	@Transactional
	public byte[] downloadImage(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is downloading image with name: {}", userIp, name);
		Image image = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
            String message = String.format("Image with name %s not found", name);
            ServiceLogger.error(message);
            return new NotFoundException(message);
		});
		
		return awsConfig.getS3Client()
				.getObjectAsBytes(
						GetObjectRequest.builder()
						.bucket(awsConfig.getBucketName())
						.key(image.getObjectKey())
						.build())
				.asByteArray();
	}
	
	@Transactional
	public String deleteImage(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for all images", userIp);
		
		Image image = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
            String message = String.format("Image with name %s not found", name);
            ServiceLogger.error(message);
            return new NotFoundException(message);
		});
		
        awsConfig.getS3Client()
        		.deleteObject(
        		DeleteObjectRequest.builder()
        		.bucket(awsConfig.getBucketName())
        		.key(image.getObjectKey())
        		.build());

        repository.delete(image);

        ServiceLogger.info("Successfully deleted image with name: {}", name);
        return "Image deleted successfully";
	}
	
	public ImageResponse addTag(String name, String tagOption) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is adding a new tag to the {} images", userIp, name);
		
	    Image image = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
	        String message = String.format("Image with name %s not found", name);
	        ServiceLogger.error(message);
	        return new NotFoundException(message);
	    });
		
		awsConfig.getS3Client()
				.putObjectTagging(PutObjectTaggingRequest.builder()
			    .bucket(awsConfig.getBucketName())
			    .key(image.getObjectKey())
			    .tagging(Tagging.builder()
			        .tagSet(
		        		List.of(
			        		Tag.builder()
			        		.key(tagOption)
			        		.value("Alpha")
			        		.build()))
			        .build())
			    .build());
		
		return mapper.fromImage(image);
	}
	
	public ImageResponse searchTag(String tagName) {
		
	}
	
	public Map<String, List<Tag>> getAllTags() {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is getting all the tags", userIp);
		
		Map<String , List<Tag>> imageTags = new HashMap<>();

	    List<Image> images = repository.findAll();
	    if (images.isEmpty()) {
	        ServiceLogger.warn("No images found for tag retrieval");
	        throw new NotFoundException("No images found in the database");
	    }
		
		for(Image image : images) {
	        GetObjectTaggingResponse response = awsConfig
	        		.getS3Client()
	        		.getObjectTagging(
	                GetObjectTaggingRequest.builder()
	                    .bucket(awsConfig.getBucketName())
	                    .key(image.getObjectKey())
	                    .build()
	            );
	            imageTags.put(image.getName(), response.tagSet());
		}
		
		return imageTags;
	}

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
	
	public List<ImageResponse> uploadMultiple(MultipartFile[] files) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is uploading an image", userIp);
	    List<ImageResponse> responses = new ArrayList<>();

	    for (MultipartFile file : files) {
	        String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
	        try {
	            awsConfig.getS3Client().putObject(PutObjectRequest.builder()
	                .bucket(awsConfig.getBucketName())
	                .key(key)
	                .contentType(file.getContentType())
	                .build(),
	                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
	            );

	            String s3Url = awsConfig.getS3Client()
	                .utilities()
	                .getUrl(builder -> builder.bucket(awsConfig.getBucketName()).key(key))
	                .toExternalForm();

	            var image = new Image();
	            merger.mergeData(image, file.getOriginalFilename(), key, s3Url, file.getContentType(), file.getSize());
	            repository.save(image);

	            responses.add(mapper.fromImage(image));

	        } catch (IOException e) {
	            throw new DataTransferenceException("Error uploading image: " + file.getOriginalFilename(), e);
	        }
	    }

	    return responses;
	}
	
	public byte[][] downloadMultiple(List<String> names) {
	    String userIp = utils.getUserIp();
	    ServiceLogger.info("IP {} is downloading multiple images: {}", userIp, names);

	    byte[][] result = new byte[names.size()][];

	    for (int i = 0; i < names.size(); i++) {
	        String name = names.get(i);
	        Image image = repository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
	            String message = String.format("Image with name %s not found", name);
	            ServiceLogger.error(message);
	            return new NotFoundException(message);
	        });

	        result[i] = awsConfig.getS3Client()
	            .getObjectAsBytes(GetObjectRequest.builder()
	                .bucket(awsConfig.getBucketName())
	                .key(image.getObjectKey())
	                .build())
	            .asByteArray();
	    }

	    return result;
	}
	
	/*
	
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
