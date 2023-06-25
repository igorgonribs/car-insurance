package com.car.insurance.api.domain.service;

import com.car.insurance.api.domain.Customer;
import com.car.insurance.api.domain.exception.CustomBusinessException;

public interface CustomerService {

	Customer getCustomerById(Integer id) throws CustomBusinessException;
}
