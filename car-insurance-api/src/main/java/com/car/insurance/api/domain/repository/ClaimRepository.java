package com.car.insurance.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

	List<Claim> findByCarId(Integer carId);
	List<Claim> findByDriverIdIn(List<Integer> ids);
}
