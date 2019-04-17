package com.everis.cars.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

/**
 * ExceptionMapper to catch CarNotFound exceptions and standardize exception
 * responses
 */
@Provider
public class CarNotFoundExceptionMapper implements ExceptionMapper<CarNotFoundException> {

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(CarNotFoundException.class);

	/**
	 * Override the default toResponse method to catch {@link CarNotFoundException}
	 * and format it as a {@link ErrorMessageCollection}.
	 * <p>
	 * This method will send a 404 status code and the
	 * {@link ErrorMessageCollection} when the requested {@link Car} from REST layer
	 * is not found
	 * </p>
	 * .
	 * 
	 * @param exception the {@link CarNotFoundException} given to be formatted
	 * @return response with 404 status code and {@link ErrorMessageCollection}
	 *         object
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final CarNotFoundException exception) {
		logger.error(exception.getMessage());
		ErrorMessageCollection errors = new ErrorMessageCollection();
		errors.addError(new ErrorMessage(exception.getMessage(), 404));

		return Response.status(Status.NOT_FOUND).entity(errors).build();
	}

}
