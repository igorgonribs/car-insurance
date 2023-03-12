package com.car.insurance.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.insurance.api.domain.security.User;
import com.car.insurance.api.dto.UserDto;
import com.car.insurance.api.service.AuthService;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthController {

	@Autowired
	private AuthService service;

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
}
