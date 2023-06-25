package com.car.insurance.api.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.security.domain.ResourceScope;

public interface ResourceScopeRepository extends JpaRepository<ResourceScope, Integer> {
}
