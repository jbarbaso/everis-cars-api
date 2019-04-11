package com.everis.cars.exceptions;

/**
 * Custom exception to notice a Car not found exception 
 */
public class CarNotFoundException extends Exception {

	/**
	 * Property required for a Serializable class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor that receives the custom message
	 * 
	 * @param String message
	 */
	public CarNotFoundException (final String message) {
		super(message);
	}

}
