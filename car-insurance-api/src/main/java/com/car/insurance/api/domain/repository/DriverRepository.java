package com.car.insurance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

}
