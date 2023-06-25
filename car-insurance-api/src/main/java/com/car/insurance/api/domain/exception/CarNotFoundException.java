package com.car.insurance.api.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarNotFoundException extends CustomBusinessException {

	private static final long serialVersionUID = -7128773727992819999L;
	private String message;

	public CarNotFoundException(String message) {
		this.message = message;
	}
}
