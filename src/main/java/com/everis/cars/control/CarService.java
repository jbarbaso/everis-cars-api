package com.everis.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.everis.cars.entity.Car;

@Stateless
public class CarService {
	
	@PersistenceContext(unitName="em_postgres")
	private static EntityManager entityManager;

	public List<Car> getCars() {
		return entityManager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
	}

	public static Car getCar( final Number carId ) throws CarNotFoundException {
		Car car = entityManager.find(Car.class, carId);

		if (car == null) {
	        throw new CarNotFoundException("Car with ID "+carId+" not found");
	    }

		return car;
	}
	
	public static Car createCar( final Car car ) {
		entityManager.persist(car);
		return car;
	}
	
	public static Car updateCar ( final Car car ) throws CarNotFoundException {
		// Throw CarNotFoundException if car doesn't exist.
		getCar(car.getId());
		
		entityManager.persist(car);
		return car;
	}
	
	public static void deleteCar ( final Number carId ) throws CarNotFoundException {
		Car car = getCar(carId);
		entityManager.remove(car);
		return;
	}
}
