package com.everis.cars.boundary;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;
import com.everis.cars.interceptors.LoggerInterceptor;
import com.everis.cars.jms.CreateCarMessageProducer;

/**
 * Rest resource implementation for car management by jms queues
 */
@Path("jms/cars")
@Interceptors(LoggerInterceptor.class)
public class CarResourceJMSImpl implements CarResourceJMS {
	
	/**
	 * {@link CarService} injected
	 * 
	 * @see com.everis.cars.control.EntityManager
	 */
	@EJB
	CarService carService;

	/**
	 * {@link CreateCarMessageProducer} injected
	 */
	@EJB
	private CreateCarMessageProducer createCarMessageProducer;
	
	/**
	 * {@link Jsonb} instance created with {@link JsonBuilder} 
	 */
	private Jsonb jsonb = JsonbBuilder.create();

	@Override
	@POST
	public Response createCar(final Car car) {
		createCarMessageProducer.produceMessage(jsonb.toJson(car), "POST");
		return Response.ok().entity(car).build();
	}

	@Override
	@PUT
	@Path("/{carId}")
	public Response updateCar(@PathParam("carId") final long carId, final Car car) throws CarNotFoundException {
		// Get the car to check if exists and throws CarNotFound if car doesn't exist
		carService.getCar(carId);
		
		car.setId(carId);
		createCarMessageProducer.produceMessage(jsonb.toJson(car), "PUT");

		return Response.ok().entity(car).build();
	}

	@Override
	@DELETE
	@Path("/{carId}")
	public Response deleteCar(@PathParam("carId") final long carId) throws CarNotFoundException {
		final Car car = carService.getCar(carId);
		createCarMessageProducer.produceMessage(jsonb.toJson(car), "DELETE");

		return Response.ok().entity(car).build();
	}

}
