package com.car.insurance.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
