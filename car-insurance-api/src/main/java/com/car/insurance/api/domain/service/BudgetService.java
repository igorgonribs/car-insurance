package com.car.insurance.api.domain.service;

import com.car.insurance.api.domain.dto.BudgetRequestDTO;
import com.car.insurance.api.domain.dto.BudgetResponseDto;
import com.car.insurance.api.domain.exception.BudgetNotFoundException;
import com.car.insurance.api.domain.exception.CustomBusinessException;

public interface BudgetService {

	void createBudget(BudgetRequestDTO dto) throws CustomBusinessException;

	void updateBudget(BudgetRequestDTO dto) throws CustomBusinessException, BudgetNotFoundException;

	BudgetResponseDto getBudget(Integer id) throws CustomBusinessException;

	void deleteBudget(Integer id) throws CustomBusinessException;
}
