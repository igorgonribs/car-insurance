package com.car.insurance.api.security.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.car.insurance.api.security.service.TokenService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if ("/api/login".equals(request.getServletPath())) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader("Authorization");
			if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = tokenService.splitToken(authorizationHeader);

					if (tokenService.isBlackListed(token))
						throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Token não é válido");

					String username = tokenService.getUserNameFromToken(token);
					String[] roles = tokenService.getRolesFromToken(token);

					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
				} catch (Exception ex) {
					log.error("Failed to authenticate client: {}", ex.getLocalizedMessage());
					response.setHeader("error", ex.getMessage());
					response.sendError(HttpStatus.FORBIDDEN.value());
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
