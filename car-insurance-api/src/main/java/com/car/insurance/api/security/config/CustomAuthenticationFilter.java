package com.car.insurance.api.security.config;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final String ACCESS_TOKEN_KEY = "token";
	private static final String ROLES_STRING = "roles";
	private final SecurityProperties properties;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, SecurityProperties properties) {
		this.authenticationManager = authenticationManager;
		this.properties = properties;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter(properties.getUsernameField());
		String password = request.getParameter(properties.getPasswordField());
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256(properties.getTokenSecret());
		String accessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + durationInMinutes(10)))
				.withIssuer(request.getRequestURL().toString())
				.withClaim(ROLES_STRING,
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		Map<String,String> token = new HashMap<>();
		token.put(ACCESS_TOKEN_KEY, accessToken);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), token);
	}

	private int durationInMinutes(int minutes) {
		return minutes * 60 * 1000;
	}
}
