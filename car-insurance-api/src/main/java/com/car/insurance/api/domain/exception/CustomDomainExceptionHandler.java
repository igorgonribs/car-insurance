package com.car.insurance.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomDomainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NoMainDriverRegisteredException.class })
	public ResponseEntity<String> handleInvalidParameters(Exception ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler({ CarNotFoundException.class, CustomerNotFoundException.class, BudgetNotFoundException.class })
	public ResponseEntity<String> notFoundException(Exception ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

}
