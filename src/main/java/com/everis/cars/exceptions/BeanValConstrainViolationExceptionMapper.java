package com.everis.cars.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;

@Provider
public class BeanValConstrainViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>  {

	@Override
	public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .build();
	}
	
	private ErrorMessageCollection prepareMessage(final ConstraintViolationException exception) {
		ErrorMessageCollection errors = new ErrorMessageCollection();
		for	(ConstraintViolation<?> cv : exception.getConstraintViolations()) {
			errors.addError(new ErrorMessage(cv.getMessage(), 400));
		}
		return errors;
	}

}
