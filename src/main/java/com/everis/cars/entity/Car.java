package com.everis.cars.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="car")
public class Car {
	@Id
	protected String id;

	@Column
	protected String brand;

	@Column
	protected Timestamp registration;

	@Column
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
