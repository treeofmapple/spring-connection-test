package com.tom.aws.awstest.product;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "product")
public class Product extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "product_name", nullable = false, unique = false)
	private String name;

	@Column(name = "quantity", nullable = true, unique = false)
	private int quantity;

	@Column(name = "price", nullable = true, unique = false)
	private BigDecimal price;

	@Column(name = "manufacturer", nullable = true, unique = false)
	private String manufacturer;

	@Column(name = "active", nullable = true, unique = false)
	private boolean active;

}
