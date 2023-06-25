package com.car.insurance.api.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SecurityProperties {

	@Value("${login.username.field.name}")
	private String usernameField;
	@Value("${login.password.field.name}")
	private String passwordField;
	@Value("${token.secret.value}")
	private String tokenSecret;
}
