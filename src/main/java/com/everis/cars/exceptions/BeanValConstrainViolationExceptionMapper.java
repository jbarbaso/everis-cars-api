package com.everis.cars.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

/**
 * ExceptionMapper to catch Bean Validation exceptions and standardize exception responses
 */
@Provider
public class BeanValConstrainViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>  {

	/**
	 * toResponse override to implement the custom response message 
	 * 
	 * @param exception the exception given to be formatted
	 * @return response with message object and current status code
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .build();
	}
	
	/**
	 * Prepare error message collection with catched exceptions
	 * 
	 * @param exception the exception to be formated
	 * @return errors list
	 */
	private ErrorMessageCollection prepareMessage(final ConstraintViolationException exception) {
		ErrorMessageCollection errors = new ErrorMessageCollection();
		for	(ConstraintViolation<?> cv : exception.getConstraintViolations()) {
			errors.addError(new ErrorMessage(cv.getMessage(), 400));
		}
		return errors;
	}

}
