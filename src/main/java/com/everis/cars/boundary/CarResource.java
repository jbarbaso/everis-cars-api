package com.everis.cars.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.everis.cars.control.CarService;
import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;

@Path("cars")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

	@Inject
	CarService carService;
	
	@Context 
	UriInfo uriInfo;

	@GET
	public List<Car> getAllCars() {
		return carService.getCars();
	}

	@POST
	public Response createCar(@Valid final Car car) {
		Car newCar = carService.createCar(car);
		String newId = String.valueOf(newCar.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newCar)
				.build();
	}
	
	@PUT
	@Path("/{carId}")
	public Response updateCar(@PathParam("carId") final long carId, @Valid final Car car) throws CarNotFoundException {
		car.setId(carId);
		Car updatedCar = carService.updateCar(car);
		
		String currentId = String.valueOf(updatedCar.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(currentId).build();
		return Response.created(uri)
				.entity(updatedCar)
				.build();
	}
	
	@DELETE
	@Path("/{carId}")
	public Car deleteCar(@PathParam("carId") final long carId) throws CarNotFoundException {
		return carService.deleteCar(carId);
	}

	@GET
	@Path("/{carId}")
	public Car getCar(@PathParam("carId") final long carId) throws CarNotFoundException {
		return carService.getCar(carId);
	}

}
