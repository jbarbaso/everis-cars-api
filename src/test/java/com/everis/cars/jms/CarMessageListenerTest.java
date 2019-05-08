package com.everis.cars.jms;

import static com.everis.cars.utils.TestUtils.mockCar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;
import com.everis.cars.exceptions.InvalidActionException;
import com.everis.cars.utils.ValidatorUtil;

/**
 * CarMessageListener testing class methods to receive messages from the queue
 * with the {@link Car} object and action to be executed
 */
@PrepareForTest({ JsonbBuilder.class, ValidatorUtil.class })
@SuppressStaticInitializationFor("com.everis.cars.utils.ValidatorUtil")
public class CarMessageListenerTest {

	/**
	 * We use the {@link Rule} annotation instead of {@link RunWith} annotation to
	 * indicate the mockito runner
	 */
	@Rule
	public PowerMockRule rule = new PowerMockRule();

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	/**
	 * {@link CarMessageListener} instance with mocks injection
	 */
	@InjectMocks
	CarMessageListener carMessageListener;

	/**
	 * {@link CarService} mocked instance
	 */
	@Mock
	CarService carService;

	/**
	 * {@link JsonbConfig} property
	 */
	private JsonbConfig jsonbConfig;

	/**
	 * {@link Jsonb} property
	 */
	private Jsonb jsonb;

	private Car car;
	private String jsonCar;
	private Message message;

	@Before
	public void setUp() {
		// mock JsonbConfig creation
		jsonbConfig = new JsonbConfig().withFormatting(true);
		// mock Jsonb creation by JsonbBuilder.create static method
		jsonb = JsonbBuilder.create(jsonbConfig);

		// mock car object
		car = mockCar("BMW", LocalDateTime.now(), "Spain");
		// mock jsonCar
		jsonCar = jsonb.toJson(car, Car.class);
		// mock message with extraInterfaces method to set allow cast it to TextMessage
		message = mock(Message.class, withSettings().extraInterfaces(TextMessage.class));

		// mockStatic class JsonBuilder to intercept the create method returning a valid
		// Jsonb object
		PowerMockito.mockStatic(JsonbBuilder.class);
		// mock Jsonb creation by JsonbBuilder.create static method
		when(JsonbBuilder.create(jsonbConfig)).thenReturn(jsonb);

		// mockStatic class ValidatorUtil to intercept the valid method in test
		PowerMockito.mockStatic(ValidatorUtil.class);
	}

	/**
	 * Mocking JMS connection, session and producer to send a message to the queue
	 * with a valid {@link Car} object and the action to be executed
	 * 
	 * @return void
	 * @throws JMSException
	 * @throws InvalidActionException
	 * @throws CarNotFoundException
	 */
	@Test
	public void ConsumeMessage_ValidMessage_ExecuteActionCorrectly()
			throws JMSException, CarNotFoundException, InvalidActionException {
		// mock message.getObjectProperty method return action String
		when(message.getObjectProperty(Mockito.anyString())).thenReturn("POST");

		// mock message.getText
		when(((TextMessage) message).getText()).thenReturn(jsonCar);

		when(ValidatorUtil.validate(car)).thenReturn(new ArrayList<String>());

		carMessageListener.onMessage(message);

		verify(message, times(1)).getObjectProperty("action");
		verify((TextMessage) message, times(1)).getText();
		verify(carService, times(1)).createCar(Mockito.any());
	}

	@Test
	public void ConsumeMessage_InvalidActionProperty_ThrowsInvalidActionException() {
		try {
			carMessageListener.executeAction(car, "ANY");
		} catch (CarNotFoundException | InvalidActionException e) {
			assertTrue(e instanceof InvalidActionException);
			assertEquals(e.getMessage(), "Invalid action ANY specified.");
		}
	}

	@Test
	public void ConsumeMessage_InvalidCar_ThrowsConstraintViolation() {
		// mock car object
		final Car invalidCar = mockCar("", LocalDateTime.now(), "Spain");
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("one error");

		when(ValidatorUtil.validate(car)).thenReturn(errors);
		try {
			carMessageListener.executeAction(invalidCar, "POST");
		} catch (CarNotFoundException | InvalidActionException e) {
//			assertTrue(e instanceof InvalidActionException);
//			assertEquals(e.getMessage(), "Invalid action ANY specified.");
		}
	}

}
