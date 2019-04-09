package com.everis.cars.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class ErrorMessageCollection {
	
	private List<ErrorMessage> errors = new ArrayList<>();

	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}
	
	public void addError(final ErrorMessage error) {
		this.errors.add(error);
	}
}
