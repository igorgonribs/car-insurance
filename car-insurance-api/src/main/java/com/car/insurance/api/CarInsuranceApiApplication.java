package com.car.insurance.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

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
import com.car.insurance.api.domain.repository.CarDriverRepository;
import com.car.insurance.api.domain.repository.CarRepository;
import com.car.insurance.api.domain.repository.ClaimRepository;
import com.car.insurance.api.domain.repository.CustomerRepository;
import com.car.insurance.api.domain.repository.DriverRepository;
import com.car.insurance.api.domain.repository.InsuranceRepository;
import com.car.insurance.api.security.domain.Resource;
import com.car.insurance.api.security.domain.ResourceScope;
import com.car.insurance.api.security.domain.Scope;
import com.car.insurance.api.security.domain.User;
import com.car.insurance.api.security.repository.ResourceRepository;
import com.car.insurance.api.security.repository.ResourceScopeRepository;
import com.car.insurance.api.security.repository.RoleRepository;
import com.car.insurance.api.security.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CarInsuranceApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository scopeRepository;

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

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ResourceScopeRepository resourceScopeRepository;
	// @Autowired
	// private BudgetRepository budgetRepository;

	public static void main(String[] args) {
		SpringApplication.run(CarInsuranceApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Inserindo scopes de teste");
		Scope scope1 = new Scope(null, "FINANCIAL_INFORMATION_API", new HashSet<>());
		scope1 = scopeRepository.save(scope1);
		Scope scope2 = new Scope(null, "COUPON_API", new HashSet<>());
		scope2 = scopeRepository.save(scope2);

		log.info("Inserindo resources de teste");
		Resource resource1 = new Resource(null, "/af/financial-information/v1/test", "GET", "financial-information-api",
				new HashSet<>());
		resource1 = resourceRepository.save(resource1);
		Resource resource2 = new Resource(null, "/af/coupon-affinity/v1/test", "GET", "coupon-affinity-api",
				new HashSet<>());
		resource2 = resourceRepository.save(resource2);

		log.info("Relacionando scope1 com resource1");
		ResourceScope resourceScope1 = new ResourceScope(resource1, scope1);
		resourceScope1 = resourceScopeRepository.save(resourceScope1);
		scope1.getResources().add(resourceScope1);
		scope1 = scopeRepository.save(scope1);
		resource1.getAllowedScopes().add(resourceScope1);
		resource1 = resourceRepository.save(resource1);

		log.info("Relacionando scope2 com resource2");
		ResourceScope resourceScope2 = new ResourceScope(resource2, scope2);
		resourceScope2 = resourceScopeRepository.save(resourceScope2);
		scope2.getResources().add(resourceScope2);
		scope2 = scopeRepository.save(scope2);
		resource2.getAllowedScopes().add(resourceScope2);
		resource2 = resourceRepository.save(resource2);

		log.info("Inserindo users de teste");
		User user1 = new User(null, "Client financial info api", "financialclient@email.com",
				passwordEncoder.encode("password"), "425.499.040-52", LocalDate.of(1996, 4, 8), Arrays.asList(scope1));
		User user2 = new User(null, "Client coupon api", "couponclient@email.com", passwordEncoder.encode("password"),
				"110.944.636-55", LocalDate.of(1994, 4, 29), Arrays.asList(scope2));
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);

		log.info("Inserindo cars de teste");
		Car car1 = new Car(null, "Corsa", "Chevrolet", "2010", 20000f, null);
		Car car2 = new Car(null, "Palio", "Fiat", "2015", 40000f, null);
		Car car3 = new Car(null, "HB20", "Hyundai", "2020", 60000f, null);
		car1 = carRepository.save(car1);
		car2 = carRepository.save(car2);
		car3 = carRepository.save(car3);

		log.info("Inserindo drivers de teste");
		Driver driver1 = new Driver(null, "Documento driver 1", LocalDate.of(1996, 4, 8));
		Driver driver2 = new Driver(null, "Documento driver 2", LocalDate.of(1980, 6, 10));
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

		// log.info("Inserindo budgets de teste");
		// Budget budget1 = new Budget(null, car1, customer1, 10000d);
		// budget1 = budgetRepository.save(budget1);
	}
}
