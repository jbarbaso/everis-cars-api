package com.everis.cars.entity;

import java.util.ArrayList;
import java.util.List;

import javax.interceptor.Interceptors;

import com.everis.cars.interceptors.LoggerInterceptor;

/**
 * ErrorMessage collection entity to standardize error responses
 */
@Interceptors(LoggerInterceptor.class)
public class ErrorMessageCollection {
	
	/**
	 * Errors list property
	 */
	private List<ErrorMessage> errors = new ArrayList<>();

	/**
	 * Get errors list
	 * 
	 * @return the error message collection
	 */
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	/**
	 * Set a new errors list
	 * 
	 * @param errors the errors list to be setted
	 * @return void
	 */
	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}
	
	/**
	 * Add a new error to errors list
	 * 
	 * @param error the error to be added to the errors list
	 * @return void
	 */
	public void addError(final ErrorMessage error) {
		this.errors.add(error);
	}
}
