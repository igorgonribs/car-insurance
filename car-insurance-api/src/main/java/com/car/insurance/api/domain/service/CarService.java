package com.car.insurance.api.domain.service;

import com.car.insurance.api.domain.Car;
import com.car.insurance.api.domain.exception.CustomBusinessException;

public interface CarService {

	Car getCarById(Integer id) throws CustomBusinessException;
}
