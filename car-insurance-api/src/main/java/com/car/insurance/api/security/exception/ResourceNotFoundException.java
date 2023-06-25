package com.car.insurance.api.security.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends AuthException {

	private static final long serialVersionUID = -7128773727992819999L;

	public ResourceNotFoundException(String urn, String method, String client, String message) {
		super(message, HttpStatus.NOT_FOUND, true, false, urn, method, client);;
	}
}
