package com.jverter.program.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class InputFieldValidatorId implements Serializable {
	private static final long serialVersionUID = 1L;
	private InputField inputField;
    private String validatorType;
}
