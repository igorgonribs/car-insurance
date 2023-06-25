package com.car.insurance.api.security.service;

import javax.servlet.http.HttpServletRequest;

import com.car.insurance.api.security.domain.User;
import com.car.insurance.api.security.dto.UserDto;
import com.car.insurance.api.security.dto.ValidateTokenRequestDto;
import com.car.insurance.api.security.dto.ValidateTokenResponseDto;
import com.car.insurance.api.security.exception.PasswordsDontMatchException;
import com.car.insurance.api.security.exception.ResourceNotAllowsScopeException;
import com.car.insurance.api.security.exception.ResourceNotFoundException;
import com.car.insurance.api.security.exception.UserNotFoundException;

public interface AuthService {
	User signUpUser(UserDto userDto) throws PasswordsDontMatchException;

	User getByCpf(String cpf) throws UserNotFoundException;

	User getLoggedUser(HttpServletRequest request) throws UserNotFoundException;

	void logout(HttpServletRequest request);

	ValidateTokenResponseDto validateAccess(ValidateTokenRequestDto request)
			throws ResourceNotFoundException, ResourceNotAllowsScopeException;
}
