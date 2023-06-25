package com.car.insurance.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverDto {
	private String driverDocument;
	private LocalDate driverBirthdate;
	private Boolean driverHasSinister;
	private Boolean mainDriver;
}
