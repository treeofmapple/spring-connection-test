package com.tom.aws.awstest.image.oldTag;

public class ImageServiceOld {


	/*
	
	public List<ItemTagResponse> getAllTags() {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is getting all the tags", userIp);
		
		List<ImageTag> matchingImages = tagRepository.findAll();
		if(matchingImages.isEmpty()) {
			ServiceLogger.warn("No tags found");
			throw new NotFoundException("No tags found in the database");
		}
		return matchingImages.stream().map(mapper::fromItemTag).collect(Collectors.toList());
	}
	
	public List<ImageTagResponse> searchTags(String tagName) {
	    String userIp = utils.getUserIp();
	    ServiceLogger.info("IP {} is searching for images with tag: {}", userIp, tagName);

	    List<ImageTag> matchingImages = tagRepository.findByTagKey(tagName);

	    if(matchingImages.isEmpty()) {
	        String message = String.format("No object found with tag: %s", tagName);
	        ServiceLogger.warn(message);
	        throw new NotFoundException(message);
	    }
	    
	    return matchingImages.stream().map(mapper::fromImageTag).collect(Collectors.toList());
	}

	@Transactional
	public ItemTagResponse createTagKey(String name) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is creating a new tag with name {}", userIp, name);
		
		ImageTag imageTag = new ImageTag();
		merger.mergeData(imageTag, name, null);
		tagRepository.save(imageTag);
		
		return mapper.fromItemTag(imageTag);
	}
	
	@Transactional
	public ItemTagResponse createTagValue(String tagKey, String tagValue) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is creating a new value {} for the tag with name {}", userIp, tagValue, tagKey);

		if(!tagRepository.existsByTagKey(tagKey)) {
	        String message = String.format("This tag doens't exist %s", tagKey);
	        ServiceLogger.warn(message);
	        throw new NotFoundException(message);
		}
		
		ImageTag existentTag = tagRepository.findByTagKeyContainingIgnoreCase(tagKey).get();
		merger.mergeData(existentTag, existentTag.getTagKey(), tagValue);
		tagRepository.save(existentTag);
		
		return mapper.fromItemTag(existentTag);
	}
	
	@Transactional
	public ItemTagResponse createTagKeyValue(String tagKey, String tagValue) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is creating a new value {} for the tag with name {}", userIp, tagValue, tagKey);

		ImageTag mergeTag = new ImageTag();
		merger.mergeData(mergeTag, tagKey, tagValue);
		
		tagRepository.save(mergeTag);
		return mapper.fromItemTag(mergeTag);
	}
	
	@Transactional
	public ImageTagResponse addTag(String name, String tagKey, String tagValue) { 
		// Search for tag value, then key if only has tag value
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is adding a new tag {} to the {} object", userIp, name);
		
	    Image images = imageRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> {
	        String message = String.format("Image with name %s not found", name);
	        ServiceLogger.error(message);
	        return new NotFoundException(message);
	    });
		
	    functions.addTags(images, tagKey, tagValue);
	    ImageTag imageTag = new ImageTag();
	    
	    merger.mergeData(imageTag, images, tagKey, tagValue);
	    tagRepository.save(imageTag);
	    
	    return mapper.fromImageTag(imageTag);
	}
	
	@Transactional
	public ImageTagResponse removeTag(String image, String tagKey, String tagValue) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is removing the tag {} from the {} object", userIp, tagKey, image);
		
		
		tagRepository.delete(null);
	}
	
	@Transactional
	public ItemTagResponse deleteTag(String tagKey, String tagValue) {
		String userIp = utils.getUserIp();
		ServiceLogger.info("IP {} is removing the tag key {} from the value {} ", userIp, tagKey, tagValue);
		
		
		
		tagRepository.delete(null);
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

	*/
	// compressed data -- Later
	
}
