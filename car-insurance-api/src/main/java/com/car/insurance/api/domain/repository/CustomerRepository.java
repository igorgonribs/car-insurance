package com.car.insurance.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
