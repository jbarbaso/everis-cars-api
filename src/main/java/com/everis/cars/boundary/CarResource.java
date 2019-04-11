package com.everis.cars.boundary;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.everis.cars.entity.Car;
import com.everis.cars.exceptions.CarNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="cars")
public interface CarResource {

	@ApiOperation( 
		value = "Fetch all Cars",
		notes = "Fetch all Cars",
		response = List.class, 
		responseContainer = "List"
	)
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Successfully fetched car collection")
	})
	@Produces(MediaType.APPLICATION_JSON)
	public List<Car> getAllCars();

	@ApiOperation( 
		value = "Create a new car",
		notes = "Send car object to create a new car",
		response = Response.class, 
		responseContainer = "Set"
	)
	@ApiResponses(value = { 
		@ApiResponse(code = 201, message = "Successfully created car"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCar(
		@ApiParam(
			value = "Car object to create a new car",
			required = true
		)
		@Valid 
		final Car car
	);

	@ApiOperation( 
		value = "Update a car",
		notes = "Send car object and the car ID in the URI to update a car",
		response = Response.class,
		responseContainer = "Set"
	)
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Successfully updated car"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 404, message = "Car not Found")
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(
			@ApiParam(
				value = "Car ID to be updated",
				required = true
			) 
			final long carId,
			@ApiParam(
				value = "Car object with new data to be updated",
				required = true
			)
			@Valid
			final Car car
	) throws CarNotFoundException;
	
	@ApiOperation( 
		value = "Remove a car",
		notes = "Send car ID in the URI to delete the car. You will get the Deleted car in the response",
		response = Response.class,
		responseContainer = "Set"
	)
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Successfully deleted car"),
		@ApiResponse(code = 404, message = "Car not Found")
	})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(
			@ApiParam(
				value = "Car ID to be deleted",
				required = true
			) 
			final long carId
	) throws CarNotFoundException;

	@ApiOperation( 
		value = "Get a car",
		notes = "Send a car ID to fetch a car",
		response = Response.class,
		responseContainer = "Set"
	)
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Successfully fetched car"),
		@ApiResponse(code = 404, message = "Car not Found")
	})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCar(
			@ApiParam(
				value = "Car ID to be fetched",
				required = true
			)
			final long carId
	) throws CarNotFoundException;
}
