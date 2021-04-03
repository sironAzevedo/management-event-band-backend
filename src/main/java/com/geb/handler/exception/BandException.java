package com.geb.handler.exception;

import lombok.Getter;

public class BandException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@Getter
	private Integer status;
	
	public BandException(String message) {
		super(message);
	}
	
	public BandException(String message, Integer status) {
		super(message);
		this.status = status;
	}
}
