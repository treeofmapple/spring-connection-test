package com.tom.aws.awstest.image;

import com.tom.aws.awstest.models.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "image", indexes = {
		@Index(name = "idx_image_name", columnList = "image_name")
})
public class Image extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image_name", 
			nullable = false)
	private String name;

	@Column(name = "object_key", 
			nullable = false, 
			unique = true)
	private String objectKey;

	@Column(name = "object_url", 
			nullable = false, 
			unique = true)
	private String objectUrl;

	@Column(name = "content_type", 
			nullable = false)
	private String contentType;

	@Column(name = "size", 
			nullable = false)
	private Long size;

}
