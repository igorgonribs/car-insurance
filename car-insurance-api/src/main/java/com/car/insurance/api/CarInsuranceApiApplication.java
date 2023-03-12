package com.car.insurance.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.car.insurance.api.domain.Car;
import com.car.insurance.api.domain.CarDriver;
import com.car.insurance.api.domain.Claim;
import com.car.insurance.api.domain.Customer;
import com.car.insurance.api.domain.Driver;
import com.car.insurance.api.domain.Insurance;
import com.car.insurance.api.domain.security.Role;
import com.car.insurance.api.domain.security.User;
import com.car.insurance.api.enums.RolesEnum;
import com.car.insurance.api.repository.CarDriverRepository;
import com.car.insurance.api.repository.CarRepository;
import com.car.insurance.api.repository.ClaimRepository;
import com.car.insurance.api.repository.CustomerRepository;
import com.car.insurance.api.repository.DriverRepository;
import com.car.insurance.api.repository.InsuranceRepository;
import com.car.insurance.api.repository.security.RoleRepository;
import com.car.insurance.api.repository.security.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CarInsuranceApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private InsuranceRepository insuranceRepository;

	@Autowired
	private CarDriverRepository carDriverRepository;

	@Autowired
	private ClaimRepository claimRepository;

	//@Autowired
	//private BudgetRepository budgetRepository;

	public static void main(String[] args) {
		SpringApplication.run(CarInsuranceApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Inserindo roles de teste");
		Role role = new Role(null, RolesEnum.EMPLOYEE.name());
		role = roleRepository.save(role);

		log.info("Inserindo users de teste");
		User user1 = new User(null, "Nome Empregado", "employee@email.com", passwordEncoder.encode("password"),
				"425.499.040-52", LocalDate.of(1996, 4, 8), Arrays.asList(role));
		User user2 = new User(null, "Nome Cliente", "client@email.com", passwordEncoder.encode("password"),
				"110.944.636-55", LocalDate.of(1994, 4, 29), null);
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);

		log.info("Inserindo cars de teste");
		Car car1 = new Car(null, "Corsa", "Chevrolet", "2010", 20000f, false, null);
		Car car2 = new Car(null, "Palio", "Fiat", "2015", 40000f, true, null);
		Car car3 = new Car(null, "HB20", "Hyundai", "2020", 60000f, false, null);
		car1 = carRepository.save(car1);
		car2 = carRepository.save(car2);
		car3 = carRepository.save(car3);

		log.info("Inserindo drivers de teste");
		Driver driver1 = new Driver(null, "Documento driver 1", LocalDate.of(1996, 4, 8), false);
		Driver driver2 = new Driver(null, "Documento driver 2", LocalDate.of(1980, 6, 10), true);
		driver1 = driverRepository.save(driver1);
		driver2 = driverRepository.save(driver2);

		log.info("Inserindo customers de teste");
		Customer customer1 = new Customer(null, "Customer 1", driver1);
		Customer customer2 = new Customer(null, "Customer 2", driver2);
		customer1 = customerRepository.save(customer1);
		customer2 = customerRepository.save(customer2);

		log.info("Inserindo insurances de teste");
		Insurance insurance1 = new Insurance(null, LocalDateTime.now(), null, true, customer1, car1);
		Insurance insurance2 = new Insurance(null, LocalDateTime.now(), null, true, customer2, car2);
		insurance1 = insuranceRepository.save(insurance1);
		insurance2 = insuranceRepository.save(insurance2);

		log.info("Inserindo cardrivers de teste");
		CarDriver carDrive1 = new CarDriver(null, driver1, car1, null, true);
		CarDriver carDrive2 = new CarDriver(null, driver2, car2, null, true);
		CarDriver carDrive3 = new CarDriver(null, driver2, car3, null, false);
		carDrive1 = carDriverRepository.save(carDrive1);
		carDrive2 = carDriverRepository.save(carDrive2);
		carDrive3 = carDriverRepository.save(carDrive3);
		
		car1.setCarDriver(Arrays.asList(carDrive1));
		car2.setCarDriver(Arrays.asList(carDrive2));
		car3.setCarDriver(Arrays.asList(carDrive3));
		car1 = carRepository.save(car1);
		car2 = carRepository.save(car2);
		car3 = carRepository.save(car3);

		log.info("Inserindo claims de teste");
		Claim claim1 = new Claim(null, LocalDateTime.now(), driver1, car1);
		Claim claim2 = new Claim(null, LocalDateTime.now(), driver2, car2);
		claim1 = claimRepository.save(claim1);
		claim2 = claimRepository.save(claim2);

		//log.info("Inserindo budgets de teste");
		//Budget budget1 = new Budget(null, car1, customer1, 10000d);
		//budget1 = budgetRepository.save(budget1);
	}
}
