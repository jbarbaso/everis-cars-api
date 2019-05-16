package com.everis.cars.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import org.apache.log4j.Logger;

/**
 * Producer helper class to connect and send messages to a queue
 */
@Stateless
public class CarMessageProducer {

	/**
	 * {@link ConnectionFactory} instance to connect with the jms connection and
	 * create the context
	 */
	@Resource(lookup = "jms/CarsApp")
	private ConnectionFactory connectionFactory;

	/**
	 * {@link Queue} connection to send messages
	 */
	@Resource(lookup = "jms/CarsAppQueue")
	private Queue queue;

	/**
	 * {@link Logger} instance
	 * 
	 * @see org.apache.log4j.Logger
	 */
	private static Logger logger = Logger.getLogger(CarMessageProducer.class);

	/**
	 * Send a message to the specified queue with a {@link Car} object parsed to
	 * json string and the action to be executed on the persistence.
	 * 
	 * @param carJsonObject expected {@link Car} object parsed as json string
	 * @param action        http method to specify the action to be executed on the
	 *                      persistence (POST, PUT, DELETE)
	 * @return void
	 */
	public void produceMessage(final String carJsonObject, String action) {
		JMSContext jmsContext = null;
		JMSProducer jmsProducer;

		try {
			logger.info("Getting context and creating producer");
			jmsContext = connectionFactory.createContext();
			jmsProducer = jmsContext.createProducer();

			logger.info("Sending the message to the queue");
			jmsProducer.setProperty("action", action).send(queue, carJsonObject);
			logger.info("Message sent to the queue successfully");
		} finally {
			logger.info("Closing context and connection");
			jmsContext.close();
		}
	}

}
