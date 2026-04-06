package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex){
		return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), null);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> handleBadRequest(BadRequestException ex){
		return buildResponse(HttpStatus.BAD_REQUEST, "Bad_Request", ex.getMessage(), null);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handValidation(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>();	
		
	    ex.getBindingResult().getFieldErrors()
	    .forEach(error ->errors.put(error.getField(), error.getDefaultMessage()));
	    
		return buildResponse(HttpStatus.BAD_REQUEST, "Bad_Request", ex.getMessage(), errors);
	}
	
	private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status, 
            String code, 
            String message, 
            Map<String, String> details) {
            
        ErrorResponse error = ErrorResponse.builder()
                .code(code)
                .message(message)
                .details(details)
                .build();
                
        return ResponseEntity.status(status).body(error);
    }
}
