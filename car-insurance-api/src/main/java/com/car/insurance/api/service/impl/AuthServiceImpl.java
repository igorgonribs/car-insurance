package com.car.insurance.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.car.insurance.api.domain.security.Role;
import com.car.insurance.api.domain.security.User;
import com.car.insurance.api.dto.UserDto;
import com.car.insurance.api.enums.RolesEnum;
import com.car.insurance.api.exception.PasswordsDontMatchException;
import com.car.insurance.api.exception.UserNotFoundException;
import com.car.insurance.api.repository.security.RoleRepository;
import com.car.insurance.api.repository.security.UserRepository;
import com.car.insurance.api.service.AuthService;
import com.car.insurance.api.service.TokenService;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenService tokenService;

	@Override
	public User signUpUser(UserDto userDto) throws PasswordsDontMatchException {

		validatePassword(userDto);

		User newUser = new User();
		newUser.setCpf(userDto.getCpf());
		newUser.setBirthdate(userDto.getBirthDate());
		newUser.setEmail(userDto.getEmail());
		newUser.setName(userDto.getName());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

		Role doctorRole = roleRepository.findByName(RolesEnum.EMPLOYEE.name());

		newUser.setRoles(Arrays.asList(doctorRole));
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

}
