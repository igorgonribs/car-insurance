package com.car.insurance.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateTokenRequestDto {

	private String token;
	private String urn;
	private String method;
	
}
