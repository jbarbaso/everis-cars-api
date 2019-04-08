package com.everis.cars.control;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.everis.cars.entity.Car;

@Stateless
public class CarService {
	
	@PersistenceContext
	private static EntityManager entityManager;

	public List<Car> getCars() {
		return entityManager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
	}

	public static Car getCar( Number carId ) {
		Car car = entityManager.find(Car.class, carId);
		return car;
	}
	
	public static Car createCar( String brand, Timestamp registration, String country ) {
		// TODO: Move this stuff to a CarFactory
		Car car = new Car();
		car.setBrand(brand);
		car.setRegistration(registration);
		car.setCountry(country);

		entityManager.persist(car);
		return car;
	}
	
	public static Car updateCar ( Car car ) {
		entityManager.persist(car);
		return car;
	}
	
	public static void deleteCar ( Number carId ) {
		Car car = getCar(carId);
		entityManager.remove(car);
		return;
	}
}
