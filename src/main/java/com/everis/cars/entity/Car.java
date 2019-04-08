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

	@Column
	@NotNull
	@Size(min=2, max=50)
	protected String brand;

	@Column
	@NotNull
	protected Timestamp registration;

	@Column
	@NotNull
	@Size(min=2, max=100)
	protected String country;

	@Column
	protected Timestamp created_at;

	@Column
	protected Timestamp updated_at;
	
	@PrePersist
	protected void onCreate () {
		created_at = (Timestamp) new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updated_at = (Timestamp) new Date();
	}
	
	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}
}
