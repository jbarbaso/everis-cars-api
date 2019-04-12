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

/**
 * Rest resource interface definition for car management
 */
@Api(value="cars")
public interface CarResource {

	/**
	 * Gets a list with all cars
	 * 
	 * @return a car list with all cars in database
	 * @see com.everis.cars.boundary.CarResource#getAllCars()
	 */
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

	/**
	 * Create and retrieve the created car
	 * 
	 * @param car the car object to be created
	 * @return response object with the created car
	 * @see com.everis.cars.boundary.CarResource#createCar(com.everis.cars.entity.Car)
	 */
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

	/**
	 * Update a car by ID
	 * 
	 * @param carId the car ID to be updated
	 * @param car the car object to update the registry
	 * @throws CarNotFoundException
	 * @return response object with updated car
	 * @see com.everis.cars.boundary.CarResource#updateCar(long, com.everis.cars.entity.Car)
	 */
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
	
	/**
	 * Delete a car by ID
	 * 
	 * @param carId the car ID to be deleted
	 * @throws CarNotFoundException
	 * @return response object with deleted car
	 * @see com.everis.cars.boundary.CarResource#deleteCar(long)
	 */
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

	
	/**
	 * Get a car by ID
	 * 
	 * @param carId the car ID to be fetched
	 * @throws CarNotFoundException
	 * @return response object with car asked
	 * @see com.everis.cars.boundary.CarResource#getCar(long)
	 */
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
