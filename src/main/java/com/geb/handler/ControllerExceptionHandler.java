package com.geb.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.InternalErrorException;
import com.geb.handler.exception.NotFoundException;
import com.geb.handler.exception.UserException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { EmptyResultDataAccessException.class, NotFoundException.class, UserException.class })
	public StandardError tNotFound(RuntimeException e, HttpServletRequest request) {
		return StandardError.builder(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date());
	}
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { InternalErrorException.class })
	public StandardError internalError(RuntimeException e, HttpServletRequest request) {
		return StandardError.builder(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		StandardError error = StandardError.builder(HttpStatus.BAD_REQUEST.value(), "Error de validação", new Date(),
				ex.getBindingResult().getFieldErrors());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(StandardError.builder(status.value(), ex.getLocalizedMessage(), new Date()),
				headers, status);
	}
}
