package com.everis.cars.timer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;

/**
 * This class check for {@link Car}s with false status 
 * and change their status to true
 */
@Singleton
public class ActivateCarTimer {
	
	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(ActivateCarTimer.class);
	
	/**
	 * Car Service injected
	 * 
	 * @see com.everis.cars.control.CarService
	 */
	@EJB
	CarService carService;

	/**
	 * {@link EntityManager} dependency injected to the service
	 * 
	 * @see javax.persistence.EntityManager
	 */
	@PersistenceContext(unitName = "em_postgres")
	protected transient EntityManager entityManager;

	/**
	 * This method is executed each 30 minutes and check for
	 * {@link Car}s with false status value to change their status to true 
	 * 
	 * @return void
	 */
	@Schedule(minute = "*/30", hour = "*")
	public void activateCars() {
		logger.info("Execute activateCars method");
		List<Car> cars = getInactiveCars();

		cars.forEach(car -> {
			car.setStatus(true);
			logger.info("Status of Car with ID: "+String.valueOf(car.getId())+" was updated to true");
			entityManager.merge(car);
			logger.info("Car with ID: "+String.valueOf(car.getId())+" was persisted");
		});
		logger.info("Ends the activateCars method execution");
	}

	/**
	 * @return a {@link Car} {@link List} with false status value
	 */
	private List<Car> getInactiveCars() {
		logger.info("Get inactive cars from database");
		final TypedQuery<Car> cars = entityManager.createNamedQuery(Car.FIND_INACTIVE_CARS, Car.class);
		return cars.getResultList();
	}

}
