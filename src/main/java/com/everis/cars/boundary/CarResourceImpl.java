package com.everis.cars.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;
import com.everis.cars.interceptors.LoggerInterceptor;
import com.everis.cars.utils.ResourceUtils;

/**
 * Rest resource implementation for car management
 */
@Path("cars")
@Interceptors(LoggerInterceptor.class)
public class CarResourceImpl implements CarResource {

	/**
	 * Car Service injected
	 * 
	 * @see com.everis.cars.control.EntityManager
	 */
	@EJB
	CarService carService;
	
	/**
	 * UriInfo class injected
	 * 
	 * @see javax.ws.rs.core.UriInfo
	 */
	@Context 
	UriInfo uriInfo;

	@Override
	@GET
	public List<Car> getAllCars() {
		return carService.getCars();
	}

	@Override
	@POST
	public Response createCar(final Car car) {
		final Car newCar = carService.createCar(car);
		final URI uri = ResourceUtils.getCreatedResourceUriWithIdPath(
				String.valueOf(newCar.getId()), 
				uriInfo);

		return Response.created(uri)
				.entity(newCar)
				.build();
	}

	@Override
	@PUT
	@Path("/{carId}")
	public Response updateCar(@PathParam("carId") final long carId, final Car car) throws CarNotFoundException {
		car.setId(carId);
		final Car updatedCar = carService.updateCar(car);

		return Response.ok()
				.entity(updatedCar)
				.build();
	}

	@Override
	@DELETE
	@Path("/{carId}")
	public Response deleteCar(@PathParam("carId") final long carId) throws CarNotFoundException {
		final Car deletedCar = carService.deleteCar(carId);
		
		return Response.ok()
				.entity(deletedCar)
				.build();
	}

	@Override
	@GET
	@Path("/{carId}")
	public Response getCar(@PathParam("carId") final long carId) throws CarNotFoundException {
		final Car car = carService.getCar(carId);
				
		return Response.ok()
				.entity(car)
				.build();
	}

}
