package com.everis.cars.jms;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.apache.log4j.Logger;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;
import com.everis.cars.exceptions.InvalidActionMessageException;
import com.everis.cars.exceptions.InvalidCarMessageException;
import com.everis.cars.utils.ValidatorUtil;

/**
 * {@link MessageListener} implementation to listen the cars queue and create,
 * update or delete received cars from queue
 * 
 * @see javax.ejb.MessageDriven
 */
@MessageDriven(mappedName = "jms/CarsAppQueue")
public class CarMessageListener implements MessageListener {

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(CarMessageListener.class);

	/**
	 * Car Service injected
	 * 
	 * @see com.everis.cars.control.EntityManager
	 */
	@EJB
	CarService carService;

	/**
	 * Initialize {@link JsonbConfig} with formatting value true
	 */
	protected final JsonbConfig config = new JsonbConfig().withFormatting(true);

	/**
	 * Initialize {@link Jsonb} with custom config
	 */
	protected final Jsonb jsonb = JsonbBuilder.create(config);

	/**
	 * Override onMessage method to intercept {@link TextMessage}s from the queue
	 * and create, update or delete received cars.
	 * 
	 * @return void
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(final Message message) {
		try {
			if (message instanceof TextMessage) {
				final String action = (String) message.getObjectProperty("action");
				final String textMessage = ((TextMessage) message).getText();
				logger.debug("Async message received [" + textMessage + "] and action " + action);

				final Car car = jsonb.fromJson(textMessage, Car.class);

				if (isValidCarMessage(car)) {
					executeAction(car, action);
				}

			} else {
				throw new InvalidCarMessageException("Message received is not TextMessage type.");
			}
		} catch (CarNotFoundException | InvalidActionMessageException | InvalidCarMessageException | JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param car
	 * @return
	 * @throws InvalidCarMessageException
	 */
	protected boolean isValidCarMessage(final Car car) throws InvalidCarMessageException {
		final ArrayList<String> errors = ValidatorUtil.validate(car);

		if (!errors.isEmpty()) {
			throw new InvalidCarMessageException(
					"Message has invalid car object. \nValidation errors: \n"+errors);
		}

		return true;
	}

	/**
	 * This method is responsible of execute the action given in the message passing
	 * received {@link Car}
	 * 
	 * @return void
	 * @throws CarNotFoundException
	 * @throws InvalidActionMessageException
	 */
	protected void executeAction(final Car car, final String action)
			throws CarNotFoundException, InvalidActionMessageException {
		switch ((String) action) {
		case "POST":
			logger.info("Creating car");
			carService.createCar(car);
			logger.info("Car created");
			break;
		case "PUT":
			logger.info("Updating car");
			carService.updateCar(car);
			logger.info("Car updated");
			break;
		case "DELETE":
			logger.info("Deleting car");
			carService.deleteCar(car.getId());
			logger.info("Car deleted");
			break;
		default:
			throw new InvalidActionMessageException("Invalid action " + action + " specified.");
		}
	}
}
