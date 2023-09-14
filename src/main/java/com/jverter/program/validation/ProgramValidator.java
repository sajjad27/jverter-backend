package com.jverter.program.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jverter.program.model.entity.Program;
import com.jverter.program.repository.ProgramRepository;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.helper.Regex;


@Service
public class ProgramValidator {
	
	@Autowired
	private ProgramRepository programRepository;
	
	public void validateProgramId(String programId) {
		if (!Regex.isPositiveNumber(programId)) {
			throw new AppException("NO_PROGRAM_ID_FOUND", "header");
		}
		Optional<Program> program = programRepository.findById(Long.parseLong(programId));
		if (program == null || !program.isPresent()) {
			throw new AppException("NO_PROGRAM_ID_FOUND", "header");
		}
	}
}
