package com.everis.cars.exceptions;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

/**
 * ExceptionMapper to catch NotAllowedException exceptions and standardize exception responses
 */
@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {
	
	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(NotAllowedException.class);

	/**
	 * Override the default toResponse method to catch {@link NotAllowedException} and format it 
	 * as a {@link ErrorMessageCollection}. 
	 * <p>This method will send a 405 status code and the {@link ErrorMessageCollection} when a
	 * request at REST layer is not allowed in the system.</p>. 
	 * 
	 * @param exception the {@link NotAllowedException} given to be formatted
	 * @return response with 405 status code and {@link ErrorMessageCollection} object
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(NotAllowedException exception) {
		logger.error(exception.getMessage());
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), Status.METHOD_NOT_ALLOWED.getStatusCode()));
		
		return Response.status(Status.METHOD_NOT_ALLOWED)
				.entity(errors)
				.build();
	}

}
