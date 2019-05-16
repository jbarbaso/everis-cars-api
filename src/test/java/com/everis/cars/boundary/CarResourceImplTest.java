package com.everis.cars.boundary;

import static com.everis.cars.utils.TestUtils.mockCar;
import static com.everis.cars.utils.TestUtils.mockCarList;
import static com.everis.cars.utils.TestUtils.mockEmptyCarList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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

/**
 * CarResource testing class methods to create, read, update and delete
 * {@link Car} objects by http requests and return the corresponding response.
 */
public class CarResourceImplTest {

	/**
	 * We use the {@link Rule} annotation instead of {@link RunWith} annotation to
	 * indicate the mockito runner
	 */
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	/**
	 * {@link CarResourceImpl} instance with mocks injection
	 */
	@InjectMocks
	CarResourceImpl carResource;

	/**
	 * {@link CarService} mocked instance
	 */
	@Mock
	CarService carService;

	/**
	 * {@link UriInfo} mocked instance
	 */
	@Mock
	UriInfo uriInfo;

	/**
	 * Getting {@link Car}s from {@link CarResource} when there is no cars on
	 * database should return an empty {@link List} of {@link Car}s.
	 * 
	 * @return void
	 */
	@Test
	public void GettingCars_NoCarsInDatabase_ReturnsEmptyListCorrectly() {

		final List<Car> cars = mockEmptyCarList();

		// mock CarService
		when(carService.getCars()).thenReturn(cars);

		// call getCars on CarResource
		final List<Car> result = carResource.getAllCars();

		// assert cars and result to be equal
		assertEquals(cars, result);

		// verify carService.getCars method was executed
		verify(carService, times(1)).getCars();
	}

	/**
	 * Getting {@link Car}s from {@link CarResource} when there are cars on database
	 * should return {@link List} of {@link Car}s.
	 * 
	 * @return void
	 */
	@Test
	public void GettingCars_ThereAreCarsInDatabase_ReturnsCarsListCorrectly() {

		final List<Car> cars = mockCarList();

		// mock CarService
		when(carService.getCars()).thenReturn(cars);

		// call getCars on CarResource
		final List<Car> result = carResource.getAllCars();

		// assert cars and result to be equal
		assertEquals(cars, result);

		// verify carService.getCars method was executed
		verify(carService, times(1)).getCars();
	}

	/**
	 * Getting single {@link Car} with {@link CarResource} with existing carId
	 * should return {@link Car} and 200 status code.
	 * 
	 * @return void
	 */
	@Test
	public void GettingCar_WithExistingCarIdParam_ReturnsResponseWith200CodeAndCarCorrectly()
			throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		// mock CarService
		when(carService.getCar(carId)).thenReturn(car);

		// call getCar on CarResource
		final Response result = carResource.getCar(carId);

		// assert status code and result status code to be equal
		assertEquals(200, result.getStatus());
		// assert entity returned is a Car object
		assertEquals(Car.class, result.getEntity().getClass());

		// verify carService.getCar method was executed
		verify(carService, times(1)).getCar(carId);
	}

	/**
	 * Getting single {@link Car} with {@link CarResource} with non existing carId
	 * throws CarNotFoundException and return 404 status code
	 * 
	 * @throws CarNotFoundException when {@link Car} ID doesn't exist
	 * @return void
	 */
	@Test
	public void GettingCar_NonExistingCarIdAs1stParam_ReturnsResponseWith404CodeAndErrorsList() {

		final long carId = 1;

		// mock CarService
		try {
			when(carService.getCar(carId)).thenThrow(new CarNotFoundException("Car with ID " + carId + " not found"));

			// execute getCar on CarResource to throw CarNotFoundException
			carResource.getCar(carId);

		} catch (CarNotFoundException e) {
			final Response result = new CarNotFoundExceptionMapper().toResponse(e);

			// assert status code and result status code to be equal
			assertEquals(404, result.getStatus());
		}
	}

	/**
	 * Creating {@link Car} with {@link CarResource} with a valid {@link Car} object
	 * returns 201 status code and created {@link Car}
	 * 
	 * @return void
	 */
	@Test
	public void CreatingCar_CarValidObject_ReturnsResponseWith201CodeAndCreatedCarCorrectly() {
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		// mock uriInfo.getAbsolutePathBuilder
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(UriBuilder.fromUri(""));

		// mock CarService.createCar
		when(carService.createCar(car)).thenReturn(car);

		// call createCar on CarResource
		final Response result = carResource.createCar(car);

		// assert status code and result status code to be equal
		assertEquals(201, result.getStatus());

		// verify carService.createCar method was executed
		verify(carService, times(1)).createCar(car);
	}

	/**
	 * Updating {@link Car} with {@link CarResource} with a valid {@link Car} object
	 * and existing {@link Car} ID returns 200 status code and updated {@link Car}
	 * 
	 * @throws CarNotFoundException when {@link Car} ID doesn't exist
	 * @return void
	 */
	@Test
	public void UpdatingCar_ExistingCarAndValidObject_ReturnsResponseWith200CodeAndUpdatedCarCorrectly()
			throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		car.setId(carId);

		// mock CarService.updateCar
		when(carService.updateCar(car)).thenReturn(car);

		// call updateCar on CarResource
		final Response result = carResource.updateCar(carId, car);

		// assert status code and result status code to be equal
		assertEquals(200, result.getStatus());

		// verify carService.updateCar method was executed
		verify(carService, times(1)).updateCar(car);
	}

	/**
	 * Updating {@link Car} with {@link CarResource} with a valid {@link Car} object
	 * and non existing {@link Car} ID returns 404 status code and updated
	 * {@link Car}
	 * 
	 * @throws CarNotFoundException when {@link Car} ID doesn't exist
	 * @return void
	 */
	@Test
	public void UpdatingCar_NonExistingCarAndValidObject_CarNotFoundExceptionThrown() throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		car.setId(carId);

		// mock CarService
		try {
			when(carService.updateCar(car)).thenThrow(new CarNotFoundException("Car with ID " + carId + " not found"));

			// execute updateCar on CarResource to throw CarNotFoundException
			carResource.updateCar(carId, car);

		} catch (CarNotFoundException e) {
			final Response result = new CarNotFoundExceptionMapper().toResponse(e);

			// assert status code and result status code to be equal
			assertEquals(404, result.getStatus());
		}
	}

	/**
	 * Deleting {@link Car} with {@link CarResource} with a valid {@link Car} ID
	 * returns 200 status code and deleted {@link Car}
	 * 
	 * @throws CarNotFoundException when {@link Car} ID doesn't exist
	 * @return void
	 */
	@Test
	public void RemovingCar_ExistingCarId_RetunrsResponseWith200CodeAndCarCorrectly() throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");

		// mock CarService.updateCar
		when(carService.deleteCar(carId)).thenReturn(car);

		// call deleteCar on CarResource
		final Response result = carResource.deleteCar(carId);

		// assert status code and result status code to be equal
		assertEquals(200, result.getStatus());

		// verify carService.deleteCar method was executed
		verify(carService, times(1)).deleteCar(carId);
	}

	/**
	 * Deleting {@link Car} with {@link CarResource} with a valid {@link Car} ID
	 * returns 200 status code and deleted {@link Car}
	 * 
	 * @throws CarNotFoundException when {@link Car} ID doesn't exist
	 * @return void
	 */
	@Test
	public void RemovingCar_NonExistingCarId_CarNotFoundExceptionThrown() throws CarNotFoundException {
		final long carId = 1;

		// mock CarService
		try {
			when(carService.deleteCar(carId))
					.thenThrow(new CarNotFoundException("Car with ID " + carId + " not found"));

			// execute deleteCar on CarResource to throw CarNotFoundException
			carResource.deleteCar(carId);

		} catch (CarNotFoundException e) {
			final Response result = new CarNotFoundExceptionMapper().toResponse(e);

			// assert status code and result status code to be equal
			assertEquals(404, result.getStatus());
		}
	}

}
