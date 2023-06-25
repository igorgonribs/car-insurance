package com.car.insurance.api.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

	String splitToken(String authorizationHeaderValue);

	String getUserNameFromToken(String token);
	
	String getUserNameFromRequest(HttpServletRequest request);

	String[] getRolesFromToken(String token);

	boolean isBlackListed(String token);

	void addToBlackList(String token);
}
