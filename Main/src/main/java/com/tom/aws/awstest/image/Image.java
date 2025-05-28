package com.tom.aws.awstest.image;

import com.tom.aws.awstest.models.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "image")
public class Image extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "image_name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "description", nullable = true, unique = false)
	private String description;

	@Column(name = "object_key", nullable = false, unique = true)
	private String objectKey;

	@Column(name = "object_url", nullable = false, unique = true)
	private String objectUrl;

	@Column(name = "content_type", nullable = false, unique = false)
	private String contentType;
	
	@Column(name = "archivated", nullable = false, unique = false)
	private Boolean archivated = false;

	@Column(name = "size", nullable = false, unique = false)
	private Long size;
	
}
