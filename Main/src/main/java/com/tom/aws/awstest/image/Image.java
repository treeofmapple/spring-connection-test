package com.tom.aws.awstest.image;

import java.util.List;

import com.tom.aws.awstest.models.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "image")
public class Image extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "image_name", nullable = false, unique = false)
	private String name;

	@Column(name = "object_key", nullable = false, unique = true)
	private String objectKey;

	@Column(name = "object_url", nullable = false, unique = true)
	private String objectUrl;

	@Column(name = "content_type", nullable = false, unique = false)
	private String contentType;

	@Column(name = "size", nullable = false, unique = false)
	private Long size;
	
    @OneToMany(mappedBy = "image",
    		cascade = CascadeType.ALL, 
    		orphanRemoval = true, 
    		fetch = FetchType.LAZY)
    private List<ImageTag> tags;
	
}
