package com.geb.handler.exception;

import lombok.Getter;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@Getter
	private Integer status;
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException(String message, Integer status) {
		super(message);
		this.status = status;
	}
}
