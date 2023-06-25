package com.car.insurance.api.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.insurance.api.domain.Claim;
import com.car.insurance.api.domain.repository.ClaimRepository;
import com.car.insurance.api.domain.service.ClaimService;

@Service
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	private ClaimRepository repository;
	@Override
	public List<Claim> claimByCarId(Integer carId) {
		return repository.findByCarId(carId);
	}

	@Override
	public List<Claim> claimByDriverIdIn(List<Integer> ids) {
		return repository.findByDriverIdIn(ids);
	}

}
