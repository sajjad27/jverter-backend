package com.jverter.program.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jverter.program.model.dao.response.ProgramSummaryResponse;
import com.jverter.program.model.entity.Program;


@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

	@Query(value = "SELECT new com.jverter.program.model.dao.response.ProgramSummaryResponse(program.id, program.title, program.description) FROM Program program ORDER BY program.id asc")
	public List<ProgramSummaryResponse> findAllPrograms();	
	
	Optional<Program> findById(Long id);

}