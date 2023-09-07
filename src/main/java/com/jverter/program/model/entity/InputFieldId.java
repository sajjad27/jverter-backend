package com.jverter.program.model.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputFieldId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Program program;
    private String name;

}