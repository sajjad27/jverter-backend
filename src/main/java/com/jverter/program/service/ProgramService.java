package com.jverter.program.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jverter.program.model.dao.response.ProgramSummaryResponse;
import com.jverter.program.model.entity.Program;
import com.jverter.program.repository.ProgramRepository;
import com.jverter.program.validation.ProgramValidator;

@Service
public class ProgramService {

	@Autowired
	private ProgramRepository programRepository;

	@Autowired
	private ProgramValidator programValidator;

	public List<ProgramSummaryResponse> getAll() {
		return this.programRepository.findAllPrograms();
	}

	public Program getProgram(String programId) {
		this.programValidator.validateProgramId(programId);
		return this.programRepository.findById(Long.parseLong(programId)).get();
	}

}
