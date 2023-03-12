package com.car.insurance.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.insurance.api.domain.Customer;
import com.car.insurance.api.exception.CustomerNotFoundException;
import com.car.insurance.api.repository.CustomerRepository;
import com.car.insurance.api.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer getCustomerById(Integer id) throws CustomerNotFoundException {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			throw new CustomerNotFoundException("Cliente não existe na base de dados.");
		}

		return customer.get();
	}

}
