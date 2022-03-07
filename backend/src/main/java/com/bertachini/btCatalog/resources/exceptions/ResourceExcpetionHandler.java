package com.bertachini.btCatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bertachini.btCatalog.services.exceptions.DataBaseExcpetion;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExcpetionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found");
		error.setMessege(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);		
	}

	@ExceptionHandler(DataBaseExcpetion.class)
	public ResponseEntity<StandardError> dataBaseViolation(DataBaseExcpetion e, HttpServletRequest request){
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("DataBase Exception");
		error.setMessege(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError error = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Validation Exception");
		error.setMessege(e.getMessage());
		error.setPath(request.getRequestURI());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			error.addError(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(error);		
	}
	
	
}
