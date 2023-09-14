package com.jverter.program.model.dao.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramSummaryResponse {

	private Long id;
	private String title;
	private String description;	
}
