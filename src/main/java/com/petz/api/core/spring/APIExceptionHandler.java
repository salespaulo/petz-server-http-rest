package com.petz.api.core.spring;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
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
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({ JwtTokenExpiredException.class, JwtTokenInvalidException.class, JwtTokenMissingException.class,
			UsernamePasswordAuthenticationException.class })
	public ErrorResponse unauthorized(HttpServletRequest request, Exception exception) {
		logger("Unauthorized error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.GLOBAL, HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ ResourceNotFoundException.class })
	public ErrorResponse badRequest(HttpServletRequest request, Exception exception) {
		logger("Not found error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.GLOBAL, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorResponse constraintException(HttpServletRequest request, Exception exception) {
		logger("Internal server error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.GLOBAL, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse exception(HttpServletRequest request, Exception exception) {
		logger("Internal server error: {}", exception);
		return ErrorResponse.of(exception.getMessage(), ErrorCode.SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getObjectName() + "." + x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

	private void logger(String msg, Exception exception) {
		log.error(msg, exception.getMessage(), exception);
	}

}
