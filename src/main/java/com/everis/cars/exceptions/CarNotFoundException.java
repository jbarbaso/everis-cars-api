package com.everis.cars.exceptions;

import org.apache.log4j.Logger;

/**
 * Custom exception to notice a Car not found exception
 */
public class CarNotFoundException extends Exception {

	/**
	 * Property required for a Serializable class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(CarNotFoundException.class);

	/**
	 * Class constructor that receives the custom message to be shown
	 * 
	 * @param message the {@link Exception} message to be shown
	 * @return void
	 */
	public CarNotFoundException(final String message) {
		super(message);
		logger.error(message);
	}

}
