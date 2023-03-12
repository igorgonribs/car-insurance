package com.car.insurance.api.service;

import javax.servlet.http.HttpServletRequest;

import com.car.insurance.api.domain.security.User;
import com.car.insurance.api.dto.UserDto;
import com.car.insurance.api.exception.PasswordsDontMatchException;
import com.car.insurance.api.exception.UserNotFoundException;

public interface AuthService {
	User signUpUser(UserDto userDto) throws PasswordsDontMatchException;

	User getByCpf(String cpf) throws UserNotFoundException;

	User getLoggedUser(HttpServletRequest request) throws UserNotFoundException;
	
	void logout(HttpServletRequest request);
}
