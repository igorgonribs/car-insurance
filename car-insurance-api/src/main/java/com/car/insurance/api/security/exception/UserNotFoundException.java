package com.car.insurance.api.security.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -7128773727992819999L;
	private String message;

	public UserNotFoundException(String message) {
		this.message = message;
	}
}
