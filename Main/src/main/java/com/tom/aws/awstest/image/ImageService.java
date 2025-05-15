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

@Service
@RequiredArgsConstructor
public class ImageService {

	private final AwsFunctions functions;
	private final ImageRepository imageRepository;
	// private final ImageTagRepository tagRepository;
	private final ImageMapper mapper;
	private final SystemUtils utils;
	private final DataMerger merger;

	public List<ImageResponse> searchAllObjects() {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for all objects", userIp);
		List<Image> images = imageRepository.findAll();
		if(images.isEmpty()) {
			ServiceLogger.warn("No products found");
			throw new NotFoundException("No products found in the database");
		}
		return images.stream().map(mapper::fromImage).collect(Collectors.toList());
	}
	
	public ImageResponse searchObjectByName(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is searching for object by name: {}", userIp, name);
		return imageRepository.findByNameContainingIgnoreCase(name)
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
		ServiceLogger.info("IP {} is uploading an object", userIp);
		String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
	    
		functions.putObject(key, file);
		
		String s3Url = functions.buildS3Url(key);
		
		var image = new Image();
	    merger.mergeData(image, file.getOriginalFilename(), key, s3Url, file.getContentType(), file.getSize());
	    imageRepository.save(image);
	    return mapper.fromImage(image);
	}
	
	@Transactional
	public byte[] downloadObject(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is downloading image with name: {}", userIp, name);
		Image images = imageRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
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
            imageRepository.save(image);

            responses.add(mapper.fromImage(image));

	    }

	    return responses;
	}
	
	@Transactional
	public byte[][] downloadMultiple(List<String> names) {
	    String userIp = utils.getUserIp();
	    ServiceLogger.info("IP {} is downloading multiple objects: {}", userIp, names);

	    byte[][] result = new byte[names.size()][];

	    for (int i = 0; i < names.size(); i++) {
	        String name = names.get(i);
	        Image images = imageRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
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
		ServiceLogger.info("IP {} is searching for all objects", userIp);
		
		Image images = imageRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
            String message = String.format("Image with name %s not found", name);
            ServiceLogger.error(message);
            return new NotFoundException(message);
		});
		
		functions.deleteObject(images);
        imageRepository.delete(images);
        ServiceLogger.info("Successfully deleted image with name: {}", name);
        return "Image deleted successfully";
	}
	
	@Transactional
	public ImageResponse renameObject(String name , String newName) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is renaming object with name: {} to: {}", userIp, name, newName);
		Image images = imageRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
            String message = String.format("Image with name %s not found", name);
            ServiceLogger.error(message);
            return new NotFoundException(message);
		});
		
		String newKey = functions.renameObject(images, newName);
		
		String newUrl = functions.buildS3Url(newKey);
		merger.mergeData(images, newKey, newUrl);
	    imageRepository.save(images);
		
		return mapper.fromImage(images);
	}
	
}
