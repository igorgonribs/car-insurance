package com.car.insurance.api.domain.exception;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends CustomBusinessException {

	private static final long serialVersionUID = -3854831705158703267L;

	private String message;

	public CustomerNotFoundException(String message) {
		this.message = message;
	}
}
