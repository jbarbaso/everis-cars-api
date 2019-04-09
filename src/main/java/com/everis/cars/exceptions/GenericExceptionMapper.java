package com.everis.cars.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(final Throwable exception) {
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), 500));
		
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errors)
				.build();
	}

}