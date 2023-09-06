package com.jverter.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jverter.program.validation.ProgramValidator;
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@InjectMocks
    private ProgramValidator programValidator;

    @Test
    public void testHello() {
        // Define the mock behavior of any methods you're using from dependencies
        // In this case, programRepository.findById is not used in the hello() method, so it's not necessary to mock it
        
        // Call the method you want to test
    	try {
    		
        programValidator.validateProgramId("1");
    	} catch (Exception e) {
			System.out.print(false);
		}
        
        // Assert the result
    }
}
