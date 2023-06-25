package com.car.insurance.api.security.service;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.interfaces.Payload;

public interface TokenService {

	String splitToken(String authorizationHeaderValue);

	String getUserNameFromToken(String token);
	
	String getUserNameFromRequest(HttpServletRequest request);

	String[] getRolesFromToken(String token);

	boolean isBlackListed(String token);

	void addToBlackList(String token);

	Payload getTokenPayload(String token);
}
