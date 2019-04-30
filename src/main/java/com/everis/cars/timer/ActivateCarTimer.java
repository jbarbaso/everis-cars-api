package com.everis.cars.timer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.apache.log4j.Logger;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;

/**
 * This class check for {@link Car}s with false status and change their status
 * to true
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
	 * This method is executed each 30 minutes and check for {@link Car}s with false
	 * status value to change their status to true
	 * 
	 * @return void
	 */
	@Schedule(minute = "*/30", hour = "*")
	public void activateCars() {
		logger.info("Execute activateCars method");
		List<Car> cars = carService.getInactivaCars();

		cars.forEach(car -> {
			car.setStatus(true);
			logger.info("Status of Car with ID: " + String.valueOf(car.getId()) + " was updated to true");
			try {
				carService.updateCar(car);
			} catch (CarNotFoundException e) {
				logger.error(e.getMessage());
			}
			logger.info("Car with ID: " + String.valueOf(car.getId()) + " was persisted");
		});
		logger.info("Ends the activateCars method execution");
	}

}
