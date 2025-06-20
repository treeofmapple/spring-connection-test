package com.tom.aws.awstest.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tom.aws.awstest.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByName(String name);

	boolean existsByName(String name);

	void deleteByName(String name);

}
