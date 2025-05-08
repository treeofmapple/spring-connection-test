package com.tom.aws.awstest.image;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tom.aws.awstest.config.AwsStorageConfig;
import com.tom.aws.awstest.exception.DataTransferenceException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.bucket-name}")
    private String bucketName;
	
	private final AwsStorageConfig awsConfig;
	private final ImageRepository repository;

	public void searchImage() {
		
	}
	
	public void uploadImage(MultipartFile file) {
		String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
		try {
		    awsConfig.s3Client().putObject(PutObjectRequest.builder()
		        .bucket("your-bucket-name")
		        .key(key)
		        .contentType(file.getContentType())
		        .build(),
		        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
	        );
	    } catch(IOException e) {
	    	throw new DataTransferenceException("Error uploading image", e);
	    }
	    String s3Url = awsConfig.s3Client()
	    		.utilities()
	    		.getUrl(builder -> builder.bucket(bucketName).key(key))
	    		.toExternalForm();
	    
	    Image image = new Image();
	    image.setName(file.getOriginalFilename());
	    image.setObjectKey(key);
	    image.setObjectUrl(s3Url);
	    image.setContentType(file.getContentType());
	    image.setSize(file.getSize());
	    repository.save(image);

	}
	
	public byte[] downloadImage(String name) {
		Image image = repository.findByName(name).orElseThrow(() -> );
	}
	
	public void deleteImage() {
		
	}
	
}
