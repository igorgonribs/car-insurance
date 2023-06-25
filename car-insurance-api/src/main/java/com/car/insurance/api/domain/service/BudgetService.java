package com.car.insurance.api.service;

import com.car.insurance.api.dto.BudgetRequestDTO;
import com.car.insurance.api.dto.BudgetResponseDto;
import com.car.insurance.api.exception.BudgetNotFoundException;
import com.car.insurance.api.exception.CustomBusinessException;

public interface BudgetService {

	void createBudget(BudgetRequestDTO dto) throws CustomBusinessException;

	void updateBudget(BudgetRequestDTO dto) throws CustomBusinessException, BudgetNotFoundException;

	BudgetResponseDto getBudget(Integer id) throws CustomBusinessException;

	void deleteBudget(Integer id) throws CustomBusinessException;
}
