package com.everis.cars.control;

public class CarNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public CarNotFoundException (String message) {
		super(message);
	}

}
