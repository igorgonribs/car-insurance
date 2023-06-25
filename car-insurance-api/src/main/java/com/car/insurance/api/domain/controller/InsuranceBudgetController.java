package com.car.insurance.api.domain.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.insurance.api.domain.dto.BudgetRequestDTO;
import com.car.insurance.api.domain.dto.BudgetResponseDto;
import com.car.insurance.api.domain.exception.CustomBusinessException;
import com.car.insurance.api.domain.service.BudgetService;
import com.car.insurance.api.security.exception.UserNotFoundException;

@RestController
@RequestMapping(value = "/api/v1/insurance/budget")
public class InsuranceBudgetController {

	@Autowired
	private BudgetService service;

	@GetMapping("/{id}")
	public ResponseEntity<BudgetResponseDto> searchBudget(@PathVariable Integer id) throws CustomBusinessException {
		BudgetResponseDto response = service.getBudget(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping()
	public ResponseEntity<Void> createBudget(@Valid @RequestBody BudgetRequestDTO budgetDto)
			throws CustomBusinessException {
		service.createBudget(budgetDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping()
	public ResponseEntity<Void> updateBudget(@Valid @RequestBody BudgetRequestDTO budgetDto)
			throws UserNotFoundException, CustomBusinessException {
		service.updateBudget(budgetDto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudget(@PathVariable Integer id)
			throws UserNotFoundException, CustomBusinessException {
		service.deleteBudget(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
