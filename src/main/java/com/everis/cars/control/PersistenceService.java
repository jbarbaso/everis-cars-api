package com.everis.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PersistenceService {

	/**
	 * {@link EntityManager} dependency injected to the service
	 * 
	 * @see javax.persistence.EntityManager
	 */
	@PersistenceContext(unitName = "em_postgres")
	// CHECKSTYLE: The visibility of this field is required for unit testing.
	protected transient EntityManager entityManager;

	/**
	 * Persist given object of any type
	 * 
	 * @param type object to be created
	 * @return object created of given type
	 */
	public <T> T create(T type) {
		this.entityManager.persist(type);
		return type;
	}

	/**
	 * Fetch all registries of given type and return a list containing them
	 * 
	 * @param type object type to be fetched
	 * @return list with all registries found of given type
	 */
	public <T> List<T> findAll(final Class<T> type) {
		final String className = type.getName();
		final TypedQuery<T> query = entityManager.createQuery("SELECT data FROM " + className + " data", type);
		return query.getResultList();
	}

	/**
	 * Fetch object from database by given type and ID
	 * 
	 * @param type the object class type
	 * @param id   numeric ID of the expected object
	 * @return object fetched by ID
	 */
	public <T> T find(final Class<T> type, final Number id) {
		return this.entityManager.find(type, id);
	}

	/**
	 * Update and retrieve the given object
	 * 
	 * @param type the object type which needs to be updated
	 * @return object updated
	 */
	public <T> T update(final T type) {
		return this.entityManager.merge(type);
	}

	/**
	 * Delete given object
	 * 
	 * @param type the object type which needs to be updated
	 * @return void
	 */
	public <T> void delete(final T type) {
		this.entityManager.remove(type);
	}

}
