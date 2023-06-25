package com.car.insurance.api.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.security.domain.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

	List<Resource> findByUrnAndHttpMethod(String urn, String method);
}
