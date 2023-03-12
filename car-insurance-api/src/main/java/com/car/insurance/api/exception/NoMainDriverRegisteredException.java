package com.car.insurance.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoMainDriverRegisteredException extends CustomBusinessException {

	private static final long serialVersionUID = -7128773727992819999L;
	private String message;

	public NoMainDriverRegisteredException(String message) {
		this.message = message;
	}
}
