package com.jverter.program.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jverter.program.model.InputFieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(InputFieldId.class)
@Entity
public class InputField {
	@JsonIgnore
	@Id
	@ManyToOne
	@JoinColumn(name = "program_id", referencedColumnName = "id")
	private Program program;

	@Id
	@Column(length = 55, nullable = false, unique = true)
	private String name;
	
    @Column(length = 55, nullable = false)
	private String label;
    
    @Column(length = 155, nullable = false)
	private String placeholder;
    
	@Column(length = 25, nullable = false)
	@Enumerated(EnumType.STRING)
	private InputFieldType inputFieldType;

	@OneToMany(mappedBy = "inputField", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InputFieldValidator> inputValidators;
}
