package com.everis.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;

@Stateless
public class CarService {
	
	/**
	 * Entity Manager
	 * 
	 * @see javax.persistence.EntityManager
	 */
	@PersistenceContext(unitName="em_postgres")
	private EntityManager entityManager;

	/**
	 * Get all cars from database
	 * 
	 * @return List<Car>
	 */
	public List<Car> getCars() {
		return entityManager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
	}

	/**
	 * Search for a car by ID.
	 * 
	 * @param Number carId
	 * @return Car
	 * @throws CarNotFoundException
	 */
	public Car getCar( final Number carId ) throws CarNotFoundException {
		final Car car = entityManager.find(Car.class, carId);

		if (car == null) {
	        throw new CarNotFoundException("Car with ID "+carId+" not found");
	    }

		return car;
	}
	
	/**
	 * Create a new car with given Car object
	 * 
	 * @param Car car
	 * @return Car
	 */
	public Car createCar( final Car car ) {
		entityManager.persist(car);
		return car;
	}
	
	/**
	 * Update a given car with new Car object 
	 * 
	 * @param Car car
	 * @return Car
	 * @throws CarNotFoundException
	 */
	public Car updateCar ( final Car car ) throws CarNotFoundException {
		// Throw CarNotFoundException if car doesn't exist.
		getCar(car.getId());

		return entityManager.merge(car);
	}
	
	/**
	 * Remove a car by ID
	 * 
	 * @param Number carId
	 * @return Car
	 * @throws CarNotFoundException
	 */
	public Car deleteCar ( final Number carId ) throws CarNotFoundException {
		Car car = getCar(carId);
		
		entityManager.remove(car);
		return car;
	}
}
