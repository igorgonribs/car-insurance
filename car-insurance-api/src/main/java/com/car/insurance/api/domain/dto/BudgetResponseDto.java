package com.car.insurance.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BudgetResponseDto {

	private Double amount;
	private CarDto car;
	private List<DriverDto> drivers;
}
