package com.everis.cars.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ErrorMessage collection entity to standardize error responses
 */
public class ErrorMessageCollection {
	
	/**
	 * Errors list property
	 */
	private List<ErrorMessage> errors = new ArrayList<>();

	/**
	 * Get errors list
	 * 
	 * @return List<ErrorMessage>
	 */
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	/**
	 * Set a new errors list
	 * 
	 * @param List<ErrorMessage> errors
	 * @return void
	 */
	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}
	
	/**
	 * Add a new error to errors list
	 * 
	 * @param error
	 * @return void
	 */
	public void addError(final ErrorMessage error) {
		this.errors.add(error);
	}
}
