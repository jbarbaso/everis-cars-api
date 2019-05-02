package com.everis.cars.jms;

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

/**
 * {@link MessageListener} implementation to listen the cars queue and create,
 * update or delete received cars from queue
 * 
 * @see javax.ejb.MessageDriven
 */
@MessageDriven(mappedName = "jms/CarsAppQueue")
public class CreateCarMessageListener implements MessageListener {

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(CreateCarMessageListener.class);

	/**
	 * Car Service injected
	 * 
	 * @see com.everis.cars.control.EntityManager
	 */
	@EJB
	CarService carService;

	/**
	 * Override onMessage method to intercept {@link TextMessage}s from the queue
	 * and create, update or delete received cars.
	 * 
	 * @return void
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		JsonbConfig config = new JsonbConfig().withFormatting(true);
		Jsonb jsonb = JsonbBuilder.create(config);
		TextMessage textMessage = null;

		try {
			if (message instanceof TextMessage) {
				String action = (String) message.getObjectProperty("action");
				textMessage = (TextMessage) message;
				logger.debug("Async message received [" + textMessage.getText() + "] and action " + action);

				Car car = jsonb.fromJson(textMessage.getText(), Car.class);

				switch ((String) action) {
				case "POST":
					logger.info("Creating the car");
					carService.createCar(car);
					break;
				case "PUT":
					logger.info("Updating the car");
					carService.updateCar(car);
					break;
				case "DELETE":
					logger.info("Deleting the car");
					carService.deleteCar(car.getId());
					break;
				}
			} else {
				logger.error("Message received is not text type");
			}
		} catch (JMSException e) {
			logger.error("JMSException executing onMessage(): " + e.toString());
		} catch (Throwable t) {
			logger.error("Exception executing onMessage():" + t.getMessage());
		}
	}

}
