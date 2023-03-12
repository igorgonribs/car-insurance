package com.car.insurance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.CarDriver;

public interface CarDriverRepository extends JpaRepository<CarDriver, Integer> {

}
