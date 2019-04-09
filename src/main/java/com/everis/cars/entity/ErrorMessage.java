package com.everis.cars.entity;

public class ErrorMessage {

	private String message;
	private int code;
	
	public ErrorMessage() {
		
	}
		
	public ErrorMessage(final String message, final int code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(final String message) {
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(final int code) {
		this.code = code;
	}
}