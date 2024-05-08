package com.book_list.resources.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.book_list.resources.exceptions.dto.StandardErrorDto;
import com.book_list.resources.exceptions.dto.ValidationErrorDto;
import com.book_list.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardErrorDto> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(convertToDto(err));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorDto> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<String> errors = e.getBindingResult()
				.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		ValidationError err = new ValidationError(status.value(), errors);
		return ResponseEntity.status(status).body(convertToDto(err));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardErrorDto> unmappedException(Exception e, HttpServletRequest request) {
		String error = "Internal server error";
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(convertToDto(err));
	}
	
	private StandardErrorDto convertToDto(StandardError standardError) {
		StandardErrorDto standardErrorDto = modelMapper.map(standardError, StandardErrorDto.class);
		return standardErrorDto;
	}
	
	private ValidationErrorDto convertToDto(ValidationError validationError) {
		ValidationErrorDto validationErrorDto = modelMapper.map(validationError, ValidationErrorDto.class);
		return validationErrorDto;
	}
}
