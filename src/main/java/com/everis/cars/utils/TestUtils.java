package com.everis.cars.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.everis.cars.entity.Car;

public class TestUtils {

	/**
	 * Helper method to quickly create and return a {@link Car} object
	 * 
	 * @param brand        string parameter with {@link Car} brand info
	 * @param registration LocalDateTime parameter with {@link Car} registration
	 *                     info
	 * @param country      string parameter with {@link Car} country info
	 * @return {@link Car} mocked object
	 */
	public static Car mockCar(String brand, LocalDateTime registration, String country) {
		final Car car = new Car();
		car.setBrand(brand);
		car.setRegistration(registration);
		car.setCountry(country);

		return car;
	}

	/**
	 * Helper method to quickly create and return a mock of a {@link List} with
	 * {@link Car}s
	 * 
	 * @return {@link List} of {@link Car}s mocked
	 */
	public static List<Car> mockCarList() {
		final List<Car> cars = new ArrayList<>();
		cars.add(mockCar("BMW", LocalDateTime.now(), "Spain"));
		cars.add(mockCar("MERCEDES", LocalDateTime.now(), "Spain"));

		return cars;
	}

	/**
	 * Helper method to quickly create and return an empty {@link List} of
	 * {@link Car}s
	 * 
	 * @return an empty {@link List} of {@link Car}s
	 */
	public static List<Car> mockEmptyCarList() {
		return new ArrayList<>();
	}

}
