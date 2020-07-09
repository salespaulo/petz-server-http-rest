package com.petz.api.core.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.petz.api.core.ErrorCode;
import com.petz.api.core.ErrorResponse;
import com.petz.api.core.exception.JwtTokenExpiredException;
import com.petz.api.core.exception.JwtTokenInvalidException;
import com.petz.api.core.exception.JwtTokenMissingException;
import com.petz.api.core.exception.ResourceNotFoundException;
import com.petz.api.core.exception.UsernamePasswordAuthenticationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler( {  
		JwtTokenExpiredException.class, 
		JwtTokenInvalidException.class, 
		JwtTokenMissingException.class, 
		UsernamePasswordAuthenticationException.class 
		})
	public ErrorResponse unauthorized(HttpServletRequest request, Exception exception) {
		logger("Unauthorized error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.GLOBAL, HttpStatus.UNAUTHORIZED);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler( {  
		ResourceNotFoundException.class
		})
	public ErrorResponse badRequest(HttpServletRequest request, Exception exception) {
		logger("Not found error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.GLOBAL, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse exception(HttpServletRequest request, Exception exception) {
		logger("Internal server error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void logger(String msg, Exception exception) {
		log.error(msg, exception.getMessage(), exception);
	}

}
