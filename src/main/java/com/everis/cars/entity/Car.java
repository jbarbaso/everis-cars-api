package com.everis.cars.entity;

import java.sql.Timestamp;
import java.util.Date;

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

@Entity
@Table(name="car")
@NamedQuery(name = Car.FIND_ALL, query = "select c from Car c")
public class Car {
	
	public static final String FIND_ALL = "Car.findAll";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	protected Number id;

	@Column(name = "brand", nullable = false, length=50)
	@NotNull(message="Brand field can't be empty.")
	@Size(min=2, max=50, message="Brand field must have a length from 2 to 50 characterers.")
	protected String brand;

	@Column(name = "registration", nullable = false)
	@NotNull(message="Registration field for Car can't be empty.")
	protected Timestamp registration;

	@Column(name = "country", nullable = false, length=100)
	@NotNull(message="Country field for Car can't be empty.")
	@Size(min=2, max=100, message="Country field must have a length from 2 to 100 characterers.")
	protected String country;

	@Column(name = "created_at", updatable = false, nullable = false)
	protected Timestamp created_at;

	@Column(name = "updated_at", nullable = false)
	protected Timestamp updated_at;
	
	@PrePersist
	protected void onCreate () {
		final Date date = new Date();
		created_at = new Timestamp(date.getTime());
		updated_at = new Timestamp(date.getTime());
	}
	
	@PreUpdate
	protected void onUpdate() {
		final Date date = new Date();
		updated_at = new Timestamp(date.getTime());
	}
	
	public Number getId() {
		return id;
	}

	public void setId(final Number id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(final Timestamp registration) {
		this.registration = registration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}
}
