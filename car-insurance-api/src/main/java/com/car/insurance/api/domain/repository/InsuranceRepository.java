package com.car.insurance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

}
