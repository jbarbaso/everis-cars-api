package com.everis.cars.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

/**
 * ExceptionMapper to catch CarNotFound exceptions and standardize exception responses
 */
@Provider
public class CarNotFoundExceptionMapper implements ExceptionMapper<CarNotFoundException> {

	/**
	 * toResponse override to implement the custom response message 
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final CarNotFoundException exception) {
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), 404));
		
		return Response.status(Status.NOT_FOUND)
				.entity(errors)
				.build();
	}

}
