package com.geb.handler.exception;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String message) {
		super(message);
	} 
}
