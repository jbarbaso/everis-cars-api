package com.everis.cars.exceptions;

import org.apache.log4j.Logger;

/**
 * Custom exception to notice that {@link com.everis.cars.entity.Car} from
 * received {@link javax.jms.Message} is not valid
 */
public class InvalidCarMessageException extends Exception {

	/**
	 * Property required for a Serializable class
	 */
	private static final long serialVersionUID = 6334255100185256304L;

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(InvalidActionMessageException.class);

	/**
	 * Class constructor that receives the custom message to be shown
	 * 
	 * @param message the {@link Exception} message to be shown
	 * @return void
	 */
	public InvalidCarMessageException(final String message) {
		super(message);
		logger.error(message);
	}

}
