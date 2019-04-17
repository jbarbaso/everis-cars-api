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
	 * {@link ErrorMessage} list property This list will contain all the
	 * {@link ErrorMessage} formatted to API REST response.
	 */
	private List<ErrorMessage> errors = new ArrayList<>();

	/**
	 * Get {@link ErrorMessage} list property
	 * 
	 * @return the error message collection
	 */
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	/**
	 * Set a new {@link ErrorMessage} list to errors property
	 * 
	 * @param errors the {@link ErrorMessage} list to be setted
	 * @return void
	 */
	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}

	/**
	 * Add a new {@link ErrorMessage} to errors property
	 * 
	 * @param error the {@link ErrorMessage} to be added to the errors property
	 * @return void
	 */
	public void addError(final ErrorMessage error) {
		this.errors.add(error);
	}
}
