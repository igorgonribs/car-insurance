package com.car.insurance.api.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateTokenResponseDto {
	private String client;
	private String urn;
	private String service;
	private String method;
	private Boolean authenticated;
	private Boolean authorized;
	private String message;
}
