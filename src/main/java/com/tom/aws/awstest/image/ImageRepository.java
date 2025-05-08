package com.tom.aws.awstest.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByName(String name);
	
	boolean existsByName(String name);
	
	@Modifying
	@Transactional
	void deleteByName(String name);
	
}
