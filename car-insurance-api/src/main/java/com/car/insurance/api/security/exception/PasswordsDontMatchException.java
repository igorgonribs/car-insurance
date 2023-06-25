package com.car.insurance.api.security.exception;

import lombok.Getter;

@Getter
public class PasswordsDontMatchException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public PasswordsDontMatchException(String message) {
		this.message = message;
	}
}
