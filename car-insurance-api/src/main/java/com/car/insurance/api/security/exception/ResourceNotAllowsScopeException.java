package com.car.insurance.api.security.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotAllowsScopeException extends AuthException {

	private static final long serialVersionUID = -2880669826079664L;

	public ResourceNotAllowsScopeException(String urn, String method, String client, String message) {
		super(message, HttpStatus.UNAUTHORIZED, true, false, urn, method, client);
	}
}
