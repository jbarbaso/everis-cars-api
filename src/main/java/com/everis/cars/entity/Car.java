package com.everis.cars.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.interceptor.Interceptors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.everis.cars.interceptors.LoggerInterceptor;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * Car entity model
 */
@Entity
@Table(name="car")
@NamedQuery(name = Car.FIND_ALL, query = "select c from Car c")
@Interceptors(LoggerInterceptor.class)
public class Car {
	
	/**
	 * Constant defined for find all cars named query
	 */
	public static final String FIND_ALL = "Car.findAll";
	
	/**
	 * Entity identifier 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	@ApiModelProperty(value = "Car ID", accessMode= AccessMode.READ_ONLY, hidden = true)
	protected Number id;

	/**
	 * Brand car string field
	 */
	@Column(name = "brand", nullable = false, length=50)
	@NotNull(message="Brand field can't be empty.")
	@Size(min=2, max=50, message="Brand field must have a length from 2 to 50 characterers.")
	protected String brand;

	/**
	 * Registration car timestamp field
	 */
	@Column(name = "registration", nullable = false)
	@NotNull(message="Registration field for Car can't be empty.")
	protected Timestamp registration;

	/**
	 * Country car string field
	 */
	@Column(name = "country", nullable = false, length=100)
	@NotNull(message="Country field for Car can't be empty.")
	@Size(min=2, max=100, message="Country field must have a length from 2 to 100 characterers.")
	protected String country;

	/**
	 * Created timestamp field
	 */
	@Column(name = "created_at", updatable = false, nullable = false)
	@ApiModelProperty(value = "Created time", accessMode= AccessMode.READ_ONLY)
	protected Timestamp created_at;

	/**
	 * Last Update timestamp field
	 */
	@Column(name = "updated_at", nullable = false)
	@ApiModelProperty(value = "Last updated time", accessMode= AccessMode.READ_ONLY)
	protected Timestamp updated_at;
	
	/**
	 * Add current timestamp values to created_at and updated_at before persist.
	 * 
	 * @return void
	 */
	@PrePersist
	protected void onCreate () {
		final Date date = new Date();
		created_at = new Timestamp(date.getTime());
		updated_at = new Timestamp(date.getTime());
	}
	
	/**
	 * Add current timestamp value to updated_at before update.
	 * 
	 * @return void
	 */
	@PreUpdate
	protected void onUpdate() {
		final Date date = new Date();
		updated_at = new Timestamp(date.getTime());
	}
	
	/**
	 * ID getter mehtod
	 * 
	 * @return car identifier
	 */
	public Number getId() {
		return id;
	}

	/**
	 * Id field setter method
	 * 
	 * @param car identifier to be setted
	 * @return void
	 */
	public void setId(final Number id) {
		this.id = id;
	}

	/**
	 * Brand getter method
	 * 
	 * @return car brand value in string format
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Brand field setter method
	 * 
	 * @param car brand value to be setted
	 * @return void
	 */
	public void setBrand(final String brand) {
		this.brand = brand;
	}

	/**
	 * Registration getter method
	 * 
	 * @return car registration value in timestamp format
	 */
	public Timestamp getRegistration() {
		return registration;
	}
	
	/**
	 * Registration field setter method
	 * 
	 * @param car registration value to be setted
	 * @return void
	 */
	public void setRegistration(final Timestamp registration) {
		this.registration = registration;
	}

	/**
	 * Country getter method
	 * 
	 * @return car country field value in string format
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Country field setter method
	 * 
	 * @param car country value to be setted
	 * @return void
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 * Created at getter method
	 * 
	 * @return car created_at field in timestamp format
	 */
	public Timestamp getCreated_at() {
		return created_at;
	}

	/**
	 * Created at setter method
	 * 
	 * @param car created_at value to be setted
	 * @return void
	 */
	public void setCreated_at(final Timestamp created_at) {
		this.created_at = created_at;
	}

	
	/**
	 * Updated at getter method
	 * 
	 * @return car updated_at field in timestamp format
	 */
	public Timestamp getUpdated_at() {
		return updated_at;
	}

	/**
	 * Updated at setter method
	 * 
	 * @param car updated_at value to be setted
	 * @return void
	 */
	public void setUpdated_at(final Timestamp updated_at) {
		this.updated_at = updated_at;
	}
}
