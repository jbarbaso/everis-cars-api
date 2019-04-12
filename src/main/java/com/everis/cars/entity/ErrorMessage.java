package com.everis.cars.entity;

import javax.interceptor.Interceptors;

import com.everis.cars.interceptors.LoggerInterceptor;

/**
 * Entity to standardize the error message response
 */
@Interceptors(LoggerInterceptor.class)
public class ErrorMessage {

	/**
	 * Message entity property 
	 */
	private String message;

	/**
	 * Code entity property 
	 */
	private int code;
	
	/**
	 * Clean constructor to instance the entity without parameters given 
	 */
	public ErrorMessage() {
		
	}

	/**
	 * Constructor with basic setters to create a new complete instance of ErrorMessage 
	 * 
	 * @param message the error message value to be setted
	 * @param code the error code value to be setted
	 * @return void
	 */
	public ErrorMessage(final String message, final int code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	/**
	 * Message getter method
	 * 
	 * @return the error message in string format
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Message field setter method
	 * 
	 * @param message the error message value to be setted 
	 */
	public void setMessage(final String message) {
		this.message = message;
	}
	
	/**
	 * Code getter method
	 * 
	 * @return the error code in int format
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Code field setter method
	 * 
	 * @param code the error code value to be setted
	 */
	public void setCode(final int code) {
		this.code = code;
	}
}