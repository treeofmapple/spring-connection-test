package com.tom.aws.awstest.image.oldTag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;

public interface ImageTagRepository extends JpaRepository<ImageTag, Long> {

	List<ImageTag> findByTagKey(String name);
	
	boolean existsByTagKey(String name);
	
	Optional<ImageTag> findByTagKeyContainingIgnoreCase(String name);
	
	@Modifying
	@Transactional
	void deleteByTagKey(String name);
	
}
