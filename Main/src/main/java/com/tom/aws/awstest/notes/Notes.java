package com.tom.aws.awstest.notes;

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
@Table(name = "notes")
public class Notes extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "note_name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "stored_note", nullable = true, unique = false)
	private byte[] objectStored;
	
	@Column(name = "description", nullable = true, unique = false)
	private String description;
	
	
}
