package com.jverter.program.controller;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jverter.program.service.ProgramService;

@RestController
@CrossOrigin
@RequestMapping("/programs")
public class ProgramController {

	
	@Autowired
	private ProgramService programService;

	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(programService.getAll());
	}	
	
	@PermitAll
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "/public/{programId}", method = RequestMethod.GET)
	public ResponseEntity<?> getProgram(@PathVariable("programId") String programId,
			HttpServletRequest request) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(programService.getProgram(programId));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{programId}", method = RequestMethod.GET)
	public ResponseEntity<?> getOneProgram(@PathVariable("programId") String programId,
			HttpServletRequest request) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(programService.getProgram(programId));
	}

}
