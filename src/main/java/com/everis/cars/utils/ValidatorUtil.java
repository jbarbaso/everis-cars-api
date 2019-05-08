package com.everis.cars.utils;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.everis.cars.timer.ActivateCarTimer;

/**
 * Helper class to transform {@link Validator} response from validate method to
 * an array with {@link ConstraintViolation} messages as string
 */
public class ValidatorUtil {

	/**
	 * Getting the {@link ValidatorFactory} from static
	 * {@link Validation}.buildDefaultValidatorFactory() method
	 */
	protected final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	/**
	 * Getting the {@link Validator} from {@link ValidFactory}.getValidator() method
	 */
	protected final static Validator validator = factory.getValidator();

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(ActivateCarTimer.class);

	/**
	 * Helper method to transform the {@link ConstraintViolation}s given from
	 * {@link Validator}.validate() method to string and return an {@link ArrayList}
	 * with {@link ConstraintViolation}s strings.
	 * 
	 * @param element the object to be validated by the {@link Validator}
	 * @return array with errors list from constraint violations
	 */
	public static <T> ArrayList<String> validate(T element) {
		ArrayList<String> errors = new ArrayList<String>();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(element);

		for (ConstraintViolation<T> violation : constraintViolations) {
			logger.error(violation.getMessage());
			errors.add(violation.getMessage());
		}

		return errors;
	}

}
