package com.car.insurance.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetNotFoundException extends CustomBusinessException {

	private static final long serialVersionUID = -7128773727992819999L;
	private String message;

	public BudgetNotFoundException(String message) {
		this.message = message;
	}
}
