package com.car.insurance.api.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.insurance.api.domain.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByCpf(String cpf);
	Optional<User> findByEmail(String email);
}
