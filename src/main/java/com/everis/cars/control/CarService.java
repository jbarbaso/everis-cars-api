package com.everis.cars.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;
import com.everis.cars.interceptors.LoggerInterceptor;

/**
 * Service to manage cars
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class CarService {
	
	@EJB
	private PersistenceService persistenceService;

	/**
	 * Get all {@link Car}s from database
	 * 
	 * @return List<Car>
	 */
	public List<Car> getCars() {
		return persistenceService.findAll(Car.class);
	}

	/**
	 * Search for a {@link Car} by ID.
	 * 
	 * @param carId the car identifier to be searched
	 * @return the car entity by given ID
	 * @throws CarNotFoundException if the specified {@link Car} ID is not found in database
	 */
	public Car getCar( final Number carId ) throws CarNotFoundException {
		final Car car = persistenceService.find(Car.class, carId);

		if (car == null) {
	        throw new CarNotFoundException("Car with ID "+carId+" not found");
	    }

		return car;
	}
	
	/**
	 * Create a new {@link Car} with given {@link Car} object
	 * 
	 * @param car the Car object to be created
	 * @return the created car
	 */
	public Car createCar( final Car car ) {
		return persistenceService.create(car);
	}
	
	/**
	 * Update a given car with new {@link Car} object
	 * 
	 * @param car the car object to be updated. Requires the ID to be updated.
	 * @return the updated car
	 * @throws CarNotFoundException if the specified {@link Car} ID is not found in database
	 */
	public Car updateCar ( final Car car ) throws CarNotFoundException {
		// Throw CarNotFoundException if car doesn't exist.
		getCar(car.getId());

		return persistenceService.update(car);
	}
	
	/**
	 * Remove a {@link Car} by ID
	 * 
	 * @param carId the car identifier to be deleted
	 * @return Car
	 * @throws CarNotFoundException if the specified {@link Car} ID is not found in database
	 */
	public Car deleteCar ( final Number carId ) throws CarNotFoundException {
		Car car = getCar(carId);

		persistenceService.delete(car);
		return car;
	}
}
