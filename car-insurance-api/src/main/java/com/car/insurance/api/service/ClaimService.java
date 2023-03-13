package com.car.insurance.api.service;

import java.util.List;

import com.car.insurance.api.domain.Claim;

public interface ClaimService {

	List<Claim> claimByCarId(Integer carId);
	List<Claim> claimByDriverIdIn(List<Integer> ids);
	
}
