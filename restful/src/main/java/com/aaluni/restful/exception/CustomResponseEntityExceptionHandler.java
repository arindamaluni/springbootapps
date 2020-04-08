package com.aaluni.restful.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aaluni.restful.user.UserNotFoundException;


@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse expResp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));	
		return new ResponseEntity(expResp, HttpStatus.INTERNAL_SERVER_ERROR); 
		
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserException(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse expResp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));	
		return new ResponseEntity(expResp, HttpStatus.NOT_FOUND); 
		
	}
	
	
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse expResp = new ExceptionResponse(new Date(), "Validation Failure", ex.getBindingResult().toString());	
		return new  ResponseEntity(expResp, HttpStatus.BAD_REQUEST); 
	}

}
