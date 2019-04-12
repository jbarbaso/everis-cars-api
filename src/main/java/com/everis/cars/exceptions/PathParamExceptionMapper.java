package com.everis.cars.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ParamException.PathParamException;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

/**
 * ExceptionMapper to catch PathParamException exceptions and standardize exception responses
 */
@Provider
public class PathParamExceptionMapper implements ExceptionMapper<PathParamException> {
	
	/**
	 * Logger instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(PathParamException.class);

	/**
	 * toResponse override to implement the custom response message 
	 * 
	 * @param exception the exception given to be formatted
	 * @return response with message object and current status code
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(PathParamException exception) {
		logger.error(exception.getMessage());
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), Status.NOT_FOUND.getStatusCode()));
		
		return Response.status(Status.NOT_FOUND)
				.entity(errors)
				.build();
	}
}