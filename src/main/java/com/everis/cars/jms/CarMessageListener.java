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
import com.everis.cars.exceptions.InvalidActionException;
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

	protected final JsonbConfig config = new JsonbConfig().withFormatting(true);
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

				logger.error("before validating");
				ArrayList<String> errors = ValidatorUtil.validate(car);
				logger.error("after validating");
				if (errors.isEmpty()) {
					logger.error(errors.toString());
					executeAction(car, action);
				} else {
					logger.error("Message has invalid car object");
					throw new InvalidActionException("Invalid action " + action + " specified.");
				}
			} else {
				logger.error("Message received is not text type");
			}
		} catch (JMSException e) {
			logger.error("JMSException executing onMessage(): " + e.toString());
		} catch (InvalidActionException i) {
			logger.error("InvalidActionException executing onMessage(): " + i.toString());
		} catch (CarNotFoundException t) {
			logger.error("CarNotFoundException executing onMessage(): " + t.toString());
		} catch (Exception e) {
			logger.error("Exception executing onMessage(): " + e.toString());
		}
	}

	/**
	 * This method is responsible of execute the action given in the message passing
	 * received {@link Car}
	 * 
	 * @return void
	 * @throws CarNotFoundException
	 * @throws InvalidActionException
	 */
	protected void executeAction(final Car car, final String action)
			throws CarNotFoundException, InvalidActionException {
		switch ((String) action) {
		case "POST":
			logger.info("Creating car from " + CarMessageListener.class);
			carService.createCar(car);
			logger.info("Car created from " + CarMessageListener.class);
			break;
		case "PUT":
			logger.info("Updating car from " + CarMessageListener.class);
			carService.updateCar(car);
			logger.info("Car updated from " + CarMessageListener.class);
			break;
		case "DELETE":
			logger.info("Deleting car from " + CarMessageListener.class);
			carService.deleteCar(car.getId());
			logger.info("Car deleted from " + CarMessageListener.class);
			break;
		default:
			throw new InvalidActionException("Invalid action " + action + " specified.");
		}
	}

}
