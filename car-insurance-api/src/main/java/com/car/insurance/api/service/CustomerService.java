package com.car.insurance.api.service;

import com.car.insurance.api.domain.Customer;
import com.car.insurance.api.exception.CustomBusinessException;

public interface CustomerService {

	Customer getCustomerById(Integer id) throws CustomBusinessException;
}
