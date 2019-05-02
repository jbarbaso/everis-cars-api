package com.everis.cars.boundary;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.everis.cars.entity.Car;
import com.everis.cars.entity.ErrorMessageCollection;
import com.everis.cars.exceptions.BeanValConstrainViolationExceptionMapper;
import com.everis.cars.exceptions.CarNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest resource interface definition for car management by jms queues
 */
@Api(value = "cars")
public interface CarResourceJMS {

	/**
	 * Send a message to the queue with the {@link Car} to be created and retrieve
	 * the {@link Car}.
	 * <ul>
	 * <li>If {@link Car} object doesn't have bean validation errors it will
	 * response with 200 status code and success message.</li>
	 * <li>If {@link Car} object has bean validation errors they will be catched and
	 * mapped from {@link BeanValConstrainViolationExceptionMapper} class and it
	 * will return a 400 status code and a {@link ErrorMessageCollection}.</li>
	 * </ul>
	 * 
	 * @param car the {@link Car} object to be created
	 * @return response object with {@link Car} sent
	 * @see com.everis.cars.boundary.CarResourceJMS#createCar(com.everis.cars.entity.Car)
	 */
	@ApiOperation(value = "Send a new car to the queue", notes = "Send new car object to the queue", response = Response.class, responseContainer = "Set")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added to the queue to create the car"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCar(
			@ApiParam(value = "Car object to be created", required = true) @Valid final Car car);

	/**
	 * Send a message to the queue with the car to be updated and retrieve the
	 * {@link Car}.
	 * <ul>
	 * <li>If {@link Car} exists and {@link Car} object doesn't have bean validation
	 * errors, it will response with 200 status code and success message.</li>
	 * <li>If {@link Car} doesn't exist it will response with 404 status code and
	 * not found message.</li>
	 * <li>If {@link Car} object has bean validation errors they will be catched and
	 * mapped from {@link BeanValConstrainViolationExceptionMapper} class and it
	 * will return a 400 status code and a {@link ErrorMessageCollection}.</li>
	 * </ul>
	 * 
	 * @param carId the {@link Car} ID to be updated
	 * @param car   the {@link Car} object to update the registry
	 * @throws CarNotFoundException if the specified {@link Car} ID is not found in
	 *                              database
	 * @return response object with updated {@link Car}
	 * @see com.everis.cars.boundary.CarResourceJMS#updateCar(long,
	 *      com.everis.cars.entity.Car)
	 */
	@ApiOperation(value = "Update a car", notes = "Send car object and car ID to the queue to update a car", response = Response.class, responseContainer = "Set")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added to the queue to update the car"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Car not Found") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(@ApiParam(value = "Car ID to be updated", required = true) final long carId,
			@ApiParam(value = "Car object with new data to be updated", required = true) @Valid final Car car)
			throws CarNotFoundException;

	/**
	 * Send a message to the queue with the car to be deleted and retrieve the
	 * {@link Car}.
	 * <ul>
	 * <li>If {@link Car} exists it will response with 200 status code and success
	 * message.</li>
	 * <li>If {@link Car} doesn't exist it will response with 404 status code and
	 * not found message.</li>
	 * </ul>
	 * 
	 * @param carId the {@link Car} ID to be deleted
	 * @throws CarNotFoundException if the specified {@link Car} ID is not found in
	 *                              database
	 * @return response object with deleted {@link Car}
	 * @see com.everis.cars.boundary.CarResourceJMS#deleteCar(long)
	 */
	@ApiOperation(value = "Remove a car", notes = "Send car ID in the URI to delete the car. You will get the deleted car in the response", response = Response.class, responseContainer = "Set")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added to the queue to delete the car"),
			@ApiResponse(code = 404, message = "Car not Found") })
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(@ApiParam(value = "Car ID to be deleted", required = true) final long carId)
			throws CarNotFoundException;
}
