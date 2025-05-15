package com.tom.aws.awstest.imagetag;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tom.aws.awstest.image.ImageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageTagService {

	private final ImageTagRepository imageTag;
	private final ImageMapper mapper;
	
	public List<ImageTagResponse> searchAllTags() {
		
		return null;
	}

	public List<ImageTagResponse> searchCategory(String name) {

		return null;
	}

	public List<ImageTagResponse> searchSubcategory(String name) {

		return null;
	}

	public ItemTagResponse createCategory(String name) {

		return null;
	}

	public ItemTagResponse createSubcategory(String name) {

		return null;
	}

	public ItemTagResponse createCategorySubcategory(String category, String subcategory) {

		return null;
	}

	public ItemTagResponse editCategory(String category, String newName) {

		return null;
	}

	public ItemTagResponse deleteCategory(String name) {

		return null;
	}

	public ItemTagResponse deleteSubcategory(String name) {

		return null;
	}
	
}
