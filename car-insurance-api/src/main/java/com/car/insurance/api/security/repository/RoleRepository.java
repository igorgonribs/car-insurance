package com.car.insurance.api.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.security.domain.Scope;

public interface RoleRepository extends JpaRepository<Scope, Integer> {

	Scope findByName(String name);
}
