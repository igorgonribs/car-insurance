package com.car.insurance.api.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.security.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);
}
