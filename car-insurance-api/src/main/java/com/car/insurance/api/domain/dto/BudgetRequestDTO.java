package com.car.insurance.api.dto;

import lombok.Data;

@Data
public class BudgetRequestDTO {

	private Integer budgetId;
	private Integer carId;
	private Integer customerId;

}
