package com.everis.cars.exceptions;

public class CarNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public CarNotFoundException (final String message) {
		super(message);
	}

}
