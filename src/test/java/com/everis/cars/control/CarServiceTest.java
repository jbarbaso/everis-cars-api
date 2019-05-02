package com.everis.cars.control;

import static com.everis.cars.utils.TestUtils.mockCar;
import static com.everis.cars.utils.TestUtils.mockCarList;
import static com.everis.cars.utils.TestUtils.mockEmptyCarList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;

/**
 * CarResource testing class methods to create, read, update and delete {@link Car} objects.
 */
public class CarServiceTest  {
	
	/**
	 * We use the {@link Rule} annotation instead of {@link RunWith} annotation
	 * to indicate the mockito runner
	 */
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	/**
	 * {@link CarService} instance with mocks injection
	 */
	@InjectMocks
	CarService carService;
	
	/**
	 * {@link PersistenceService} mocked instance
	 */
	@Mock
	PersistenceService persistenceService;
	
	/**
	 * Get a {@link Car} {@link List} when there are {@link Car}s in database
	 * and is returned correctly.
	 * 
	 * @return void
	 */
	@Test
	public void GettingCars_ThereAreCarsInDatabase_ReturnsCarsListCorrectly () {
		List<Car> cars = mockCarList();
		
		// mock persistenceService findAll method
		when(persistenceService.findAll(Car.class)).thenReturn(cars);
		
		// use carService.getCars method
		final List<Car> result = carService.getCars();
		
		// assert result length is equal to cars length
		assertEquals(cars.toArray().length, result.toArray().length);
		
		// assert brand field of first cars value is equal to brand field of first result value
		assertEquals(cars.get(0).getBrand(),result.get(0).getBrand());
	}
	
	/**
	 * Get an empty {@link Car} {@link List} when there aren't {@link Car}s in database
	 * and is returned correctly.
	 * 
	 * @return void
	 */
	@Test
	public void GettingCars_NoCarsInDatabase_ReturnsEmptyListCorrectly () {
		List<Car> cars = mockEmptyCarList();
		
		// use carService.getCars method
		final List<Car> result = carService.getCars();
		
		// assert result length is equal to cars length
		assertEquals(cars.toArray().length, result.toArray().length);
	}
	
	/**
	 * Get a {@link Car} by existing carId and returns the {@link Car} correctly
	 * 
	 * @return void
	 * @throws CarNotFoundException if given carId doesn't exist
	 */
	@Test
	public void GettingCar_WithExistingCarIdParam_ReturnsCarCorrectly () throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		
		// mock persistenceService find method
		when(persistenceService.find(Car.class, carId)).thenReturn(car);
		
		// use carService.getCar method
		final Car result = carService.getCar(carId);
		
		// assert brand field is equal to car and result objects
		assertEquals(car.getBrand(), result.getBrand());
		
		// verify persistenceService.find method was executed
		verify(persistenceService, times(1)).find(Car.class, carId);
	}

	/**
	 * Try to get a {@link Car} and throws {@link CarNotFoundException} 
	 * because given carId doesn't exist.
	 * 
	 * @return void
	 * @throws CarNotFoundException if given carId doesn't exist
	 */
	@Test(expected=CarNotFoundException.class)
	public void GettingCar_WithNonExistingCarIdParam_CarNotFoundExceptionThrown () throws CarNotFoundException {
		final long carId = 1;
		
		// use carService.getCar method
		carService.getCar(carId);
	}

	/**
	 * Create and return a new {@link Car} with given valid {@link Car} object
	 * 
	 * @return void
	 */
	@Test
	public void CreatingCar_CarValidObject_ReturnsCarCorrectly() {
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		
		// mock persistenceService create
		when(persistenceService.create(car)).thenReturn(car);
		
		// use carService.createCar method
		final Car result = carService.createCar(car);
		
		// assert car and result are equal
		assertEquals(car, result);
		// assert brand field is equal to car and result objects
		assertEquals(car.getBrand(), result.getBrand());
		
		// verify persistenceService.create method was executed
		verify(persistenceService, times(1)).create(car);
	}

	/**
	 * Create a {@link Car} and throws an exception because an invalid field value
	 * in given {@link Car} object.
	 * <p>We expect a {@link Throwable} class because it's not necessary to test bean validations.
	 * The main objective of this method is to test that we get an exception when there are invalid
	 * values in the {@link Car} object to be created.</p>
	 * 
	 * @return void
	 */
	@Test(expected=Throwable.class)
	public void CreatingCar_CarNonValidObject_ConstraintViolationException() {
		// mock a Car with invalid values
		final Car car = mockCar("", LocalDateTime.now(), "Spain");
		
		// mock persistenceService create
		when(persistenceService.create(car)).thenThrow(Throwable.class);
		
		// use getCars method
		carService.createCar(car);
	}

	/**
	 * Update and return a new {@link Car} with given valid {@link Car} object
	 * 
	 * @return void
	 * @throws CarNotFoundException when given {@link Car} ID doesn't exists
	 */
	@Test
	public void UpdatingCar_ExistingCarAndValidObject_ReturnsCarCorrectly() throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		car.setId(carId);
		
		// mock persistenceService find method
		when(persistenceService.find(Car.class, car.getId())).thenReturn(car);
		// mock persistenceService update method
		when(persistenceService.update(car)).thenReturn(car);
		
		// use carService.createCar method
		final Car result = carService.updateCar(car);
		
		// assert car and result are equal
		assertEquals(car, result);
		// assert brand field is equal to car and result objects
		assertEquals(car.getBrand(), result.getBrand());
		
		// verify persistenceService.create method was executed
		verify(persistenceService, times(1)).update(car);
	}
	
	/**
	 * Try to update the {@link Car} object but throws a {@link CarNotFoundException}
	 * because given carId doesn't exist.
	 * 
	 * @return void
	 * @throws CarNotFoundException when given {@link Car} ID doesn't exists
	 */
	@Test(expected=CarNotFoundException.class)
	public void UpdatingCar_NonExistingCarAndValidObject_ReturnsCarCorrectly() throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		car.setId(carId);
		
		// use carService.createCar method
		carService.updateCar(car);
	}
	
	/**
	 * Delete a {@link Car} by existing carId and returns deleted {@link Car} correctly
	 * 
	 * @return void
	 * @throws CarNotFoundException if given carId doesn't exist
	 */
	@Test
	public void DeletingCar_WithExistingCarIdParam_RemoveCarAndReturnsCarCorrectly () throws CarNotFoundException {
		final long carId = 1;
		final Car car = mockCar("BMW", LocalDateTime.now(), "Spain");
		car.setId(carId);
		
		// mock persistenceService find method
		when(persistenceService.find(Car.class, carId)).thenReturn(car);
		
		// use carService.getCar method
		final Car result = carService.deleteCar(carId);
		
		// assert brand field is equal to car and result objects
		assertEquals(car.getBrand(), result.getBrand());

		// verify persistenceService.find method was executed
		verify(persistenceService, times(1)).find(Car.class, carId);
		// verify persistenceService.delete method was executed
		verify(persistenceService, times(1)).delete(car);
	}
	
	/**
	 * Try to delete {@link Car} by non existing carId and throws a {@link CarNotFoundException}.
	 * 
	 * @return void
	 * @throws CarNotFoundException if given carId doesn't exist
	 */
	@Test(expected=CarNotFoundException.class)
	public void DeletingCar_WithNonExistingCarIdParam_CarNotFoundExceptionThrown () throws CarNotFoundException {
		final long carId = 1;
		
		// use carService.delteCar method
		carService.deleteCar(carId);
	}
	
}
