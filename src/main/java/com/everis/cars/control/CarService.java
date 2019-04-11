package com.everis.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;

@Stateless
public class CarService {
	
	@PersistenceContext(unitName="em_postgres")
	private EntityManager entityManager;

	public List<Car> getCars() {
		return entityManager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
	}

	public Car getCar( final Number carId ) throws CarNotFoundException {
		final Car car = entityManager.find(Car.class, carId);

		if (car == null) {
	        throw new CarNotFoundException("Car with ID "+carId+" not found");
	    }

		return car;
	}
	
	public Car createCar( final Car car ) {
		entityManager.persist(car);
		return car;
	}
	
	public Car updateCar ( final Car car ) throws CarNotFoundException {
		// Throw CarNotFoundException if car doesn't exist.
		getCar(car.getId());

		return entityManager.merge(car);
	}
	
	public Car deleteCar ( final Number carId ) throws CarNotFoundException {
		Car car = getCar(carId);
		
		entityManager.remove(car);
		return car;
	}
}
