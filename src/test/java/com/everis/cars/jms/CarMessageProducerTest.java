package com.everis.cars.jms;

import static com.everis.cars.utils.TestUtils.mockCar;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.everis.cars.entity.Car;

/**
 * CarMessageProducer testing class methods to send messages to the queue with the {@link Car}
 * object and action to be executed
 */
public class CarMessageProducerTest {

	/**
	 * We use the {@link Rule} annotation instead of {@link RunWith} annotation to
	 * indicate the mockito runner
	 */
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	/**
	 * {@link CarMessageProducer} instance with mocks injection
	 */
	@InjectMocks
	CarMessageProducer carMessageProducer;

	/**
	 * {@link ConnectionFactory} mocked instance
	 */
	@Mock
	private ConnectionFactory connectionFactory;

	/**
	 * {@link Queue} mocked instance
	 */
	@Mock
	private Queue queue;

	/**
	 * {@link JMSContext} mocked instance
	 */
	@Mock
	private JMSContext jmsContext;

	/**
	 * {@link JMSProducer} mocked instance
	 */
	@Mock
	private JMSProducer jmsProducer;

	/**
	 * {@link Jsonb} instance created with {@link JsonBuilder}
	 */
	private Jsonb jsonb = JsonbBuilder.create();

	/**
	 * Mocking JMS connection, session and producer to send a message to the queue
	 * with a valid {@link Car} object and the action to be executed
	 * 
	 * @return void
	 */
	@Test
	public void SendingMessage_ValidTextMessage_MessageSentCorrectly() {
		// mock car object
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		// mock jmsContext creation from connectionFactory mocked
		when(connectionFactory.createContext()).thenReturn(jmsContext);
		// mock jmsProducer creation from jmsContext mocked
		when(jmsContext.createProducer()).thenReturn(jmsProducer);
		// mock setProperty method to allow any string parameter as argument 1 and 2 and
		// return jmsProducer
		when(jmsProducer.setProperty(Mockito.anyString(), Mockito.anyString())).thenReturn(jmsProducer);
		// mock setProperty method to return jmsProducer
		when(jmsProducer.send(queue, jsonb.toJson(car))).thenReturn(jmsProducer);

		// execute the method to be mocked
		carMessageProducer.produceMessage(jsonb.toJson(car), "POST");

		// verify setProperty method is executed and received specified values
		verify(jmsProducer, times(1)).setProperty("action", "POST");
		// verify send method is executed and received specified values
		verify(jmsProducer, times(1)).send(queue, jsonb.toJson(car));
	}

}
