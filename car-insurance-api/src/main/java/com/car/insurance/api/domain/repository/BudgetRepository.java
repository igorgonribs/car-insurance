package com.car.insurance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.insurance.api.domain.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

}
