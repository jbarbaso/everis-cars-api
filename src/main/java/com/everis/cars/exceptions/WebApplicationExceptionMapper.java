package com.everis.cars.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

/**
 * ExceptionMapper to catch WebApplicationException exceptions and standardize exception responses
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
	
	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(WebApplicationException.class);

	/**
	 * Override the default toResponse method to catch {@link WebApplicationException} and format it 
	 * as a {@link ErrorMessageCollection}. 
	 * <p>This method will send a 400 status code and the {@link ErrorMessageCollection}</p>.
	 * 
	 * @param exception the {@link WebApplicationException} given to be formatted
	 * @return response with 400 status code and {@link ErrorMessageCollection} object
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(WebApplicationException exception) {
		logger.error(exception.getMessage());
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), Status.BAD_REQUEST.getStatusCode()));
		
		return Response.status(Status.BAD_REQUEST)
				.entity(errors)
				.build();
	}
}
