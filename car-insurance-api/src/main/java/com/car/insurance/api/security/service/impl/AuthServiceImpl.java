package com.car.insurance.api.security.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Payload;
import com.car.insurance.api.security.domain.Resource;
import com.car.insurance.api.security.domain.ResourceScope;
import com.car.insurance.api.security.domain.Scope;
import com.car.insurance.api.security.domain.User;
import com.car.insurance.api.security.dto.UserDto;
import com.car.insurance.api.security.dto.ValidateTokenRequestDto;
import com.car.insurance.api.security.dto.ValidateTokenResponseDto;
import com.car.insurance.api.security.exception.PasswordsDontMatchException;
import com.car.insurance.api.security.exception.ResourceNotAllowsScopeException;
import com.car.insurance.api.security.exception.ResourceNotFoundException;
import com.car.insurance.api.security.exception.UserNotFoundException;
import com.car.insurance.api.security.repository.ResourceRepository;
import com.car.insurance.api.security.repository.RoleRepository;
import com.car.insurance.api.security.repository.UserRepository;
import com.car.insurance.api.security.service.AuthService;
import com.car.insurance.api.security.service.TokenService;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository scopeRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public User signUpUser(UserDto userDto) throws PasswordsDontMatchException {

		validatePassword(userDto);

		User newUser = new User();
		newUser.setCpf(userDto.getCpf());
		newUser.setBirthdate(userDto.getBirthDate());
		newUser.setEmail(userDto.getEmail());
		newUser.setName(userDto.getName());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

		Scope scope = scopeRepository.findByName(userDto.getScope());

		newUser.setRoles(Arrays.asList(scope));
		return repository.save(newUser);
	}

	private void validatePassword(UserDto userDto) throws PasswordsDontMatchException {
		if (!userDto.getPassword().equals(userDto.getPasswordConfirmation()))
			throw new PasswordsDontMatchException("Senhas informadas não batem.");
	}

	@Override
	public User getByCpf(String cpf) throws UserNotFoundException {
		Optional<User> user = repository.findByCpf(cpf);

		if (user.isEmpty())
			throw new UserNotFoundException("Usuário não encontrado para o cpf informado.");

		return user.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(username);

		if (user.isEmpty())
			throw new UsernameNotFoundException("Usuário não encontrado na base de dados.");

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.get().getRoles().stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
				authorities);
	}

	@Override
	public User getLoggedUser(HttpServletRequest request) throws UserNotFoundException {
		String username = tokenService.getUserNameFromRequest(request);
		Optional<User> user = repository.findByEmail(username);

		if (user.isEmpty())
			throw new UsernameNotFoundException("Usuário não encontrado na base de dados.");

		return user.get();
	}

	@Override
	public void logout(HttpServletRequest request) {
		tokenService.addToBlackList(request.getHeader("Authorization").substring("Bearer ".length()));
	}

	@Override
	public ValidateTokenResponseDto validateAccess(ValidateTokenRequestDto request)
			throws ResourceNotFoundException, ResourceNotAllowsScopeException {
		Payload payload = tokenService.getTokenPayload(request.getToken());

		List<String> roles = getRolesInToken(payload);
		List<Resource> resource = getResources(request, payload);

		validateTokenAuthorization(payload.getSubject(), roles, resource.get(0));

		return ValidateTokenResponseDto.builder()
				.authenticated(true)
				.authorized(true)
				.method(request.getMethod())
				.service(resource.get(0).getService())
				.urn(request.getUrn())
				.client(payload.getSubject())
				.message(String.format("User %s is allowed to perform %s on resource %s", payload.getSubject(), request.getMethod(), request.getUrn()))
				.build();
	}

	private void validateTokenAuthorization(String client, List<String> roles, Resource resource) throws ResourceNotAllowsScopeException {
		Optional<ResourceScope> scope = resource.getAllowedScopes().stream()
				.filter(rs -> roles.contains(rs.getScope().getName())).findFirst();

		if (scope.isEmpty())
			throw new ResourceNotAllowsScopeException(resource.getUrn(),resource.getHttpMethod(), client, String.format(
					"Scope inside token is not allowed to perform %s on resource %s. Provided scopes were: %s",
					resource.getHttpMethod(), resource.getUrn(), roles.toString()));
	}

	private List<String> getRolesInToken(Payload payload) {
		Map<String, Claim> claims = payload.getClaims();
		List<String> roles = claims.get("roles").asList(String.class);
		return roles;
	}

	private List<Resource> getResources(ValidateTokenRequestDto request, Payload payload)
			throws ResourceNotFoundException {
		
		List<Resource> resources = resourceRepository.findByUrnAndHttpMethod(request.getUrn(), request.getMethod());

		if (resources.isEmpty())
			throw new ResourceNotFoundException(request.getUrn(),request.getMethod(), payload.getSubject(), 
					String.format("Requested resource '%s' with method '%s' not found in database.", request.getUrn(), request.getMethod()));
		
		return resources;
	}

}
