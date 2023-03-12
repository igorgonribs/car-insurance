package com.car.insurance.api.service;

import com.car.insurance.api.domain.Car;
import com.car.insurance.api.exception.CustomBusinessException;

public interface CarService {

	Car getCarById(Integer id) throws CustomBusinessException;
}
