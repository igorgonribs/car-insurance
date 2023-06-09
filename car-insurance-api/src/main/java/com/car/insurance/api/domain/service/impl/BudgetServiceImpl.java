package com.car.insurance.api.domain.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.insurance.api.domain.Budget;
import com.car.insurance.api.domain.Car;
import com.car.insurance.api.domain.CarDriver;
import com.car.insurance.api.domain.Customer;
import com.car.insurance.api.domain.dto.BudgetRequestDTO;
import com.car.insurance.api.domain.dto.BudgetResponseDto;
import com.car.insurance.api.domain.dto.CarDto;
import com.car.insurance.api.domain.dto.DriverDto;
import com.car.insurance.api.domain.exception.BudgetNotFoundException;
import com.car.insurance.api.domain.exception.CustomBusinessException;
import com.car.insurance.api.domain.exception.NoMainDriverRegisteredException;
import com.car.insurance.api.domain.repository.BudgetRepository;
import com.car.insurance.api.domain.service.BudgetService;
import com.car.insurance.api.domain.service.CarService;
import com.car.insurance.api.domain.service.ClaimService;
import com.car.insurance.api.domain.service.CustomerService;

@Service
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private CarService carService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ClaimService claimService;

	@Autowired
	private BudgetRepository budgetRepository;

	@Override
	public void createBudget(BudgetRequestDTO dto) throws CustomBusinessException {
		Car car = carService.getCarById(dto.getCarId());
		Customer customer = customerService.getCustomerById(dto.getCustomerId());

		double amount = calculateAmount(car);
		Budget budget = Budget.builder()
				.amount(amount)
				.car(car)
				.customer(customer)
				.build();
		budgetRepository.save(budget);
	}

	private double calculateAmount(Car car) throws NoMainDriverRegisteredException {
		double baseFipeValue = 0.06;
		int risks = evaluateRisks(car);
		return car.getFipeValue() * (baseFipeValue + (0.02 * risks));
	}

	private int evaluateRisks(Car car) throws NoMainDriverRegisteredException {
		int risks = 0;
		
		Optional<CarDriver> mainDriver = car.getCarDriver().stream().filter(CarDriver::getMainDriver).findFirst();
		
		if(mainDriver.isEmpty())
			throw new NoMainDriverRegisteredException("O carro informado não possui condutor principal cadastrado");
		
		int mainDriverAge = Period.between(mainDriver.get().getDriver().getBirthdate(), LocalDate.now()).getYears();
		if(mainDriverAge <= 25 && mainDriverAge >= 18) {
			risks++;
		}
		
		List<Integer> driverIds  = car.getCarDriver().stream().map(item -> item.getDriver().getId()).collect(Collectors.toList());
		if(!claimService.claimByDriverIdIn(driverIds).isEmpty()) {
			risks++;
		}
		
		if(!claimService.claimByCarId(car.getId()).isEmpty()) {
			risks++;
		}
		return risks;
	}

	@Override
	public void updateBudget(BudgetRequestDTO dto) throws CustomBusinessException, BudgetNotFoundException {
		Budget budget = getById(dto.getBudgetId());
		Car car = carService.getCarById(dto.getCarId());
		double amount = calculateAmount(car);
		
		budget.setAmount(amount);
		budget.setCar(car);
		budgetRepository.save(budget);
	}

	private Budget getById(Integer id) throws BudgetNotFoundException {
		Optional<Budget> budget = budgetRepository.findById(id);
		if(budget.isEmpty())
			throw new BudgetNotFoundException("Orçamento não existe na base de dados");
		return budget.get();
	}


	@Override
	public BudgetResponseDto getBudget(Integer id) throws BudgetNotFoundException {
		Budget budget = getById(id);
		return mountResponse(budget);
	}

	private BudgetResponseDto mountResponse(Budget budget) {
		CarDto carDto = CarDto.builder()
				.carManufacturer((budget.getCar().getManufacturer()))
				.carModel(budget.getCar().getModel())
				.carYear(budget.getCar().getReleaseYear())
				.build();

		List<DriverDto> driverDtoList = new ArrayList<>();
		budget.getCar().getCarDriver().stream().forEach(
				carDriver -> driverDtoList.add(DriverDto.builder()
						.driverBirthdate(carDriver.getDriver().getBirthdate())
						.driverDocument(carDriver.getDriver().getDocument())
						.mainDriver(carDriver.getMainDriver())
						.build()));

		BudgetResponseDto response = BudgetResponseDto.builder()
				.amount(budget.getAmount()).car(carDto)
				.drivers(driverDtoList).build();
		return response;
	}

	@Override
	public void deleteBudget(Integer id) throws BudgetNotFoundException {
		Optional<Budget> budget = budgetRepository.findById(id);
		
		if(budget.isEmpty())
			throw new BudgetNotFoundException("Orçamento não existe na base de dados");
		
		budgetRepository.deleteById(id);
	}

}
