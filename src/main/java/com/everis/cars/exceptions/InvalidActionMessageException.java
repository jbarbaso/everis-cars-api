package com.everis.cars.exceptions;

import org.apache.log4j.Logger;

/**
 * Custom exception to notice that action from received
 * {@link javax.jms.Message} is not valid
 */
public class InvalidActionMessageException extends Exception {

	/**
	 * Property required for a Serializable class
	 */
	private static final long serialVersionUID = -7933361652377700615L;

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
	public InvalidActionMessageException(final String message) {
		super(message);
		logger.error(message);
	}

}
