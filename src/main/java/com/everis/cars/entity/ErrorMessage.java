package com.everis.cars.entity;

/**
 * Entity to standardize the error message response
 */
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
	 * @param message
	 * @param code
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
	 * @return String message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Message field setter method
	 * 
	 * @param String message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}
	
	/**
	 * Code getter method
	 * 
	 * @return int code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Code field setter method
	 * 
	 * @param int code
	 */
	public void setCode(final int code) {
		this.code = code;
	}
}