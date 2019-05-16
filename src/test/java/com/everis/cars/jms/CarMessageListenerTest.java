package com.everis.cars.jms;

import static com.everis.cars.utils.TestUtils.mockCar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import com.everis.cars.exceptions.InvalidActionMessageException;
import com.everis.cars.exceptions.InvalidCarMessageException;
import com.everis.cars.utils.ValidatorUtil;

/**
 * CarMessageListener testing class methods to receive messages from the queue
 * with the {@link Car} object and action to be executed
 */
@PrepareForTest({ JsonbBuilder.class, ValidatorUtil.class })
@SuppressStaticInitializationFor("com.everis.cars.utils.ValidatorUtil")
public class CarMessageListenerTest {

	/**
	 * We use the {@link PowerMockRule} with {@link Rule} annotation to indicate the
	 * mockito runner
	 */
	@Rule
	public PowerMockRule rule = new PowerMockRule();

	/**
	 * We use the {@link MockitoRule} with {@link Rule} annotation to indicate the
	 * powermock runner
	 */
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

	/**
	 * Mock a {@link Car} instance to be used in different tests
	 */
	private Car car;

	/**
	 * Mock a {@link Car} instance in json format to be used in different tests
	 */
	private String jsonCar;

	/**
	 * Mock a {@link Message} instance to be used in different tests
	 */
	private Message message;

	/**
	 * Initialize some utils and mocks to be used for different tests
	 */
	@Before
	public void setUp() {
		// mock Jsonb with custom config
		jsonbConfig = new JsonbConfig().withFormatting(true);
		jsonb = JsonbBuilder.create(jsonbConfig);

		// mockStatic class JsonBuilder class
		PowerMockito.mockStatic(JsonbBuilder.class);

		// mock Jsonb creation by JsonbBuilder.create static method
		when(JsonbBuilder.create(jsonbConfig)).thenReturn(jsonb);

		// mock car and jsonCar objects
		car = mockCar("BMW", LocalDateTime.now(), "Spain");
		jsonCar = jsonb.toJson(car, Car.class);

		// mock message with extraInterfaces method to allow cast it to TextMessage
		message = mock(Message.class, withSettings().extraInterfaces(TextMessage.class));

		// mockStatic class ValidatorUtil
		PowerMockito.mockStatic(ValidatorUtil.class);
	}

	/**
	 * Consume a message from the queue with a valid {@link Car} object and the
	 * action to be executed. Will execute onMessage method and execute the action
	 * given correctly.
	 * 
	 * @return void
	 * @throws JMSException                  if there is a jms error
	 * @throws InvalidActionMessageException if there is an invalid action in given
	 *                                       message
	 * @throws CarNotFoundException          if there is not car in database if
	 *                                       action requires update or delete
	 */
	@Test
	public void ConsumeMessage_ValidMessage_ExecuteActionCorrectly()
			throws JMSException, CarNotFoundException, InvalidActionMessageException {
		// mock message.getObjectProperty method return action String
		when(message.getObjectProperty(Mockito.anyString())).thenReturn("POST");

		// mock message.getText
		when(((TextMessage) message).getText()).thenReturn(jsonCar);
		when(ValidatorUtil.validate(car)).thenReturn(new ArrayList<String>());

		// when execute onMessage
		carMessageListener.onMessage(message);

		// then verify methods execution
		verify(message, times(1)).getObjectProperty("action");
		verify((TextMessage) message, times(1)).getText();
		verify(carService, times(1)).createCar(Mockito.any());
	}

	/**
	 * Consume a message from the queue with a valid {@link Car} object but invalid
	 * action value. On executeAction will throw an
	 * {@link InvalidActionMessageException}.
	 */
	@Test
	public void ConsumeMessage_InvalidActionProperty_ThrowsInvalidActionException() {
		try {
			// given car object form setUp method and invalid action
			// when execute executeAction method
			carMessageListener.executeAction(car, "ANY");
		} catch (CarNotFoundException | InvalidActionMessageException e) {
			// then this throws an InvalidActionMessageException
			assertTrue(e instanceof InvalidActionMessageException);
			assertEquals(e.getMessage(), "Invalid action ANY specified.");
		}
	}

	/**
	 * Consume a message from the queue with invalid {@link Car} value. On
	 * isValidCarMessage method execution it will throw an
	 * {@link InvalidCarMessageException}.
	 */
	@Test
	public void ConsumeMessage_InvalidCar_ThrowsConstraintViolation() {
		// given invalid car and errors from ValidatorUtil
		final Car invalidCar = mockCar("", LocalDateTime.now(), "Spain");
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Field brand can't be null");

		// when validate return errors
		when(ValidatorUtil.validate(any())).thenReturn(errors);
		try {
			// when execute isValidCarMessage method
			carMessageListener.isValidCarMessage(invalidCar);
		} catch (InvalidCarMessageException e) {
			// then this throws an InvalidCarMessageException
			assertTrue(e instanceof InvalidCarMessageException);
			assertEquals(e.getMessage(), "Message has invalid car object. \nValidation errors: \n" + errors);
		}
	}

}
