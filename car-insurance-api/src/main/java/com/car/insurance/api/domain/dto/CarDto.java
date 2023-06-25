package com.car.insurance.api.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {

	private String carModel;
	private String carManufacturer;
	private String carYear;
	
}
