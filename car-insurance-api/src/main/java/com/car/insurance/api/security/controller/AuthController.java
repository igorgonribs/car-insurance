package com.car.insurance.api.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.insurance.api.security.domain.User;
import com.car.insurance.api.security.dto.UserDto;
import com.car.insurance.api.security.dto.ValidateTokenRequestDto;
import com.car.insurance.api.security.dto.ValidateTokenResponseDto;
import com.car.insurance.api.security.service.AuthService;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/login")
	public ResponseEntity<String> logIn(HttpServletRequest request) {
		//service.logout(request);
		return ResponseEntity.ok().body("Usuário logado com sucesso.");
	}
	
	@GetMapping("/logoff")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		service.logout(request);
		return ResponseEntity.ok().body("Usuário deslogado com sucesso.");
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@Valid @RequestBody UserDto userDto) throws Exception {
		User userCreated = service.signUpUser(userDto);
		return ResponseEntity.created(null).body(userCreated);
	}

	@PostMapping("/validate-token")
	public ResponseEntity<ValidateTokenResponseDto> validateAuthorization(@RequestBody ValidateTokenRequestDto request)
			throws Exception {
		ValidateTokenResponseDto response = service.validateAccess(request);
		return ResponseEntity.ok().body(response);
	}
}
