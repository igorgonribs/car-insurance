package com.car.insurance.api.security.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AuthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2708368720144761180L;

	protected String message;
	protected HttpStatus httpStatus;
	protected Boolean authenticated;
	protected Boolean authorized;
	protected String method;
	protected String client;
	protected String urn;
}
