package com.tom.aws.awstest.image.oldTag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;

public interface ImageTagRepositoryOld extends JpaRepository<ImageTagOld, Long> {

	List<ImageTagOld> findByTagKey(String name);
	
	boolean existsByTagKey(String name);
	
	Optional<ImageTagOld> findByTagKeyContainingIgnoreCase(String name);
	
	@Modifying
	@Transactional
	void deleteByTagKey(String name);
	
}
