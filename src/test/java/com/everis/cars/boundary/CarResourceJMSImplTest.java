package com.everis.cars.boundary;

import static com.everis.cars.utils.TestUtils.mockCar;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;
import com.everis.cars.exceptions.CarNotFoundExceptionMapper;
import com.everis.cars.jms.CarMessageProducer;

/**
 * CarResourceJMS testing class methods to send messages to the queue with the
 * objective to create, update and delete {@link Car} objects and return the
 * corresponding response.
 */
public class CarResourceJMSImplTest {

	/**
	 * We use the {@link Rule} annotation instead of {@link RunWith} annotation to
	 * indicate the mockito runner
	 */
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	/**
	 * {@link CarResourceJMSImpl} instance with mocks injection
	 */
	@InjectMocks
	CarResourceJMSImpl carResourceJMS;

	/**
	 * {@link CarService} mocked instance
	 */
	@Mock
	CarService carService;

	/**
	 * {@link CarMessageProducer} mocked instance
	 */
	@Mock
	CarMessageProducer carMessageProducer;

	/**
	 * {@link Jsonb} instance created with {@link JsonBuilder}
	 */
	private Jsonb jsonb = JsonbBuilder.create();

	/**
	 * Sending message to the queue with a valid {@link Car} object to be created
	 * returns 200 status code and send the message to the queue
	 * 
	 * @return void
	 */
	@Test
	public void SendingCarToTheQueue_CarValidObjectToBeCreated_ReturnsResponseWith200CodeAndSendMessageCorrectly() {
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		// call creatCar on CarResourceJMS
		final Response result = carResourceJMS.createCar(car);

		// assert status code and result status code to be equal
		assertEquals(200, result.getStatus());

		// verify carMessageProducer.produceMessage method was executed correctly
		verify(carMessageProducer, times(1)).produceMessage(jsonb.toJson(car), "POST");
	}

	/**
	 * Sending message to the queue with a valid {@link Car} object to be updated
	 * returns 200 status code and send the message to the queue
	 * 
	 * @return void
	 */
	@Test
	public void SendingCarToTheQueue_CarValidObjectToBeUpdated_ReturnsResponseWith200CodeAndSendMessageCorrectly()
			throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		when(carService.getCar(carId)).thenReturn(car);

		// execute updateCar on carResourceJMS
		final Response result = carResourceJMS.updateCar(carId, car);

		// assert status code and result status code to be equal
		assertEquals(200, result.getStatus());

		// verify carMessageProducer.produceMessage method was executed correctly
		verify(carMessageProducer, times(1)).produceMessage(jsonb.toJson(car), "PUT");
	}

	/**
	 * Sending message to the queue with a non existing {@link Car} ID to be updated
	 * throws CarNotFoundException
	 * 
	 * @return void
	 */
	@Test
	public void SendingCarToTheQueue_InvalidCarIdToBeUpdated_ThrowsCarNotFoundException() {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		try {
			// mock CarService.getCar to throw CarNotFoundException
			when(carService.getCar(carId)).thenThrow(new CarNotFoundException("Car with ID " + carId + " not found"));

			// execute updateCar on carResourceJMS and throws CarNotFoundException
			carResourceJMS.updateCar(carId, car);

		} catch (CarNotFoundException e) {
			final Response result = new CarNotFoundExceptionMapper().toResponse(e);

			// assert status code and result status code to be equal
			assertEquals(404, result.getStatus());
		}
	}

	/**
	 * Sending message to the queue with a valid {@link Car} ID to be deleted
	 * returns 200 status code and send the message to the queue
	 * 
	 * @return void
	 */
	@Test
	public void SendingCarToTheQueue_CarValidObjectToBeDeleted_ReturnsResponseWith200CodeAndSendMessageCorrectly()
			throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		// mock CarService.getCar
		when(carService.getCar(carId)).thenReturn(car);

		// execute deleteCar on CarResource to throw CarNotFoundException
		final Response result = carResourceJMS.deleteCar(carId);

		// assert status code and result status code to be equal
		assertEquals(200, result.getStatus());

		// verify carMessageProducer.produceMessage method was executed correctly
		verify(carMessageProducer, times(1)).produceMessage(jsonb.toJson(car), "DELETE");
	}

	/**
	 * Sending message to the queue with a non existing {@link Car} ID to be deleted
	 * throws CarNotFoundException
	 * 
	 * @return void
	 */
	@Test
	public void SendingCarToTheQueue_InvalidCarIdToBeDeleted_ThrowsCarNotFoundException() {
		final long carId = 1;

		try {
			// mock CarService
			when(carService.getCar(carId)).thenThrow(new CarNotFoundException("Car with ID " + carId + " not found"));

			// execute deleteCar on carResourceJMS to throw CarNotFoundException
			carResourceJMS.deleteCar(carId);

		} catch (CarNotFoundException e) {
			final Response result = new CarNotFoundExceptionMapper().toResponse(e);

			// assert status code and result status code to be equal
			assertEquals(404, result.getStatus());
		}
	}
}
