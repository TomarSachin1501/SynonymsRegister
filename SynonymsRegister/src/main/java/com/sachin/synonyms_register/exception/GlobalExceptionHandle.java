package com.sachin.synonyms_register.exception;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sachin.synonyms_register.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandle {
	
	@ExceptionHandler(WordNotFoundException.class)
	public ResponseEntity<ApiResponse> handleWordNotFoundException(WordNotFoundException ex)
	{
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(),Collections.emptyList(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(apiResponse,new HttpHeaders(),apiResponse.getStatus());
	}
	
	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<ApiResponse> handleUnsupportedOperationOperationException(UnsupportedOperationException ex)
	{
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), Collections.emptyList(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(apiResponse, new HttpHeaders(),apiResponse.getStatus());
	}

}
