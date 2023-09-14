package com.jverter.program.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(InputFieldValidatorId.class)
@Entity
public class InputFieldValidator implements Serializable {

	@JsonIgnore
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "program_id", referencedColumnName = "program_id"),
			@JoinColumn(name = "input_field_name", referencedColumnName = "name") })
	private InputField inputField;

//	@Id
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "input_field_name", referencedColumnName = "name")
//	@Column(name = "input_field_name", length = 25)
//	private String name;

	@Id
	@Column(name = "InputValidatorType", length = 25)
	private String validatorType;

	@Column(nullable = false, length = 155)
	private String message;

}
