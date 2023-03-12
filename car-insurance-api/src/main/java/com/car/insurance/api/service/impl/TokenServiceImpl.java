package com.car.insurance.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.car.insurance.api.security.SecurityProperties;
import com.car.insurance.api.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	private List<String> blackList = new ArrayList<>();
	@Autowired
	private SecurityProperties properties;

	public String splitToken(String authorizationHeaderValue) {
		return authorizationHeaderValue.substring("Bearer ".length());
	}

	public String getUserNameFromToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(properties.getTokenSecret());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJwt = verifier.verify(token);
		return decodedJwt.getSubject();
	}

	@Override
	public boolean isBlackListed(String token) {
		return blackList.stream().anyMatch(item -> token.equals(item));
	}

	/**
	 * Para invalidar o token o ideal seria, ao efetivar o logoff, adicionar o token
	 * a uma blacklist em cache (redis, por exemplo), removendo-o do cache após
	 * algum tempo. Entretanto, para agilizar o desenvolvimento do desafio, estou
	 * usando apenas uma lista em memória para representar esta blacklist.
	 */
	@Override
	public void addToBlackList(String token) {
		blackList.add(token);
	}

	@Override
	public String[] getRolesFromToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(properties.getTokenSecret());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJwt = verifier.verify(token);
		return decodedJwt.getClaim("roles").asArray(String.class);
	}

	@Override
	public String getUserNameFromRequest(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring("Bearer ".length());
		Algorithm algorithm = Algorithm.HMAC256(properties.getTokenSecret());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJwt = verifier.verify(token);
		return decodedJwt.getSubject();
	}
}
