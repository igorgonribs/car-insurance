package com.car.insurance.api.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.car.insurance.api.security.dto.ValidateTokenResponseDto;

@ControllerAdvice
public class CustomSecurityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ PasswordsDontMatchException.class })
	public ResponseEntity<String> handleInvalidParameters(Exception ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<String> notFoundException(Exception ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ValidateTokenResponseDto> handleResourceNotAllowsScopeException(AuthException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ValidateTokenResponseDto.builder()
				.authenticated(ex.getAuthenticated())
				.authorized(false)
				.message(ex.getMessage())
				.client(ex.getClient())
				.method(ex.getMethod())
				.urn(ex.getUrn())
				.build());
	}
}
