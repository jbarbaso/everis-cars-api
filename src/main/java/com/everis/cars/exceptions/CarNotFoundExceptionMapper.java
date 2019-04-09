package com.everis.cars.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

@Provider
public class CarNotFoundExceptionMapper implements ExceptionMapper<CarNotFoundException> {

	@Override
	public Response toResponse(final CarNotFoundException exception) {
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), 404));
		
		return Response.status(Status.NOT_FOUND)
				.entity(errors)
				.build();
	}

}
