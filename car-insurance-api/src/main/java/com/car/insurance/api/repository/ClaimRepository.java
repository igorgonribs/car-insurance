package com.car.insurance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

}
