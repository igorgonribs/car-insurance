package com.car.insurance.api.domain.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.insurance.api.domain.Car;
import com.car.insurance.api.domain.exception.CarNotFoundException;
import com.car.insurance.api.domain.repository.CarRepository;
import com.car.insurance.api.domain.service.CarService;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Override
	public Car getCarById(Integer id) throws CarNotFoundException {
		Optional<Car> car = carRepository.findById(id);
		if (car.isEmpty()) {
			throw new CarNotFoundException("Carro não existe na base de dados.");
		}
		return car.get();
	}

}
