package com.everis.cars.control;

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
	
	public static Car createCar( Car car ) {
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
