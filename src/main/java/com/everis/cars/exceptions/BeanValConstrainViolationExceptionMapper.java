package com.everis.cars.exceptions;

import javax.interceptor.Interceptors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.everis.cars.entity.ErrorMessage;
import com.everis.cars.entity.ErrorMessageCollection;
import com.everis.cars.interceptors.LoggerInterceptor;

/**
 * ExceptionMapper to catch Bean Validation exceptions and standardize exception responses
 */
@Provider
@Interceptors(LoggerInterceptor.class)
public class BeanValConstrainViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>  {

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(BeanValConstrainViolationExceptionMapper.class);
	
	/**
	 * Override the default toResponse method to catch the bean validation errors from 
	 * {@link ConstraintViolationException} and format it as a {@link ErrorMessageCollection}. 
	 * <p>This method will send a 400 status code and the {@link ErrorMessageCollection} when
	 * object given from REST layer fail on bean validations</p>.
	 * 
	 * @param exception the {@link ConstraintViolationException} given to construct the response
	 * @return response with {@link ErrorMessageCollection} and 400 status code
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .build();
	}
	
	/**
	 * Prepare {@link ErrorMessageCollection} and transform the {@link ConstraintViolationException} 
	 * messages to {@link ErrorMessage} format.
	 * 
	 * @param exception the {@link ConstraintViolationException} to be formatted
	 * @return errors {@link ErrorMessageCollection} with {@link ErrorMessage} added from {@link ConstraintViolationException}
	 */
	private ErrorMessageCollection prepareMessage(final ConstraintViolationException exception) {
		ErrorMessageCollection errors = new ErrorMessageCollection();
		for	(ConstraintViolation<?> cv : exception.getConstraintViolations()) {
			logger.error(cv.getMessage());
			errors.addError(new ErrorMessage(cv.getMessage(), 400));
		}
		return errors;
	}

}
