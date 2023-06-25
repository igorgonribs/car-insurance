package com.car.insurance.api.exception;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler({ PasswordsDontMatchException.class, NoMainDriverRegisteredException.class })
	public ResponseEntity<String> handleInvalidParameters(Exception ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler({ UserNotFoundException.class, CarNotFoundException.class, CustomerNotFoundException.class,
			BudgetNotFoundException.class })
	public ResponseEntity<String> notFoundException(Exception ex) {
		String errorMessage = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArguments(MethodArgumentNotValidException ex) {
		String errorMessage = new ArrayList<>(ex.getAllErrors()).get(0).getDefaultMessage();
		return ResponseEntity.badRequest().body(errorMessage);
	}
}
