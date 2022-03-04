package com.ajaguilar.apiRestful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long>{
	/**
	 * Metodo que devuelve un trabajador por un nombre.
	 * @param nombre del obrero que buscamos.
	 * @return obrero con ese nombre.
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM worker WHERE worker.name LIKE %?1%", nativeQuery = true)
	Worker findByName(String name) throws IllegalArgumentException;
	
	/**
	 * Metodo que devuelve un trabajador por un apellido.
	 * @param apellido del obrero que buscamos.
	 * @return obrero con ese apellido.
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM worker WHERE worker.surname LIKE %?1%", nativeQuery = true)
	Worker findBySurname(String surname) throws IllegalArgumentException;

	/**

	 * Metodo que devuelve un trabajador por su email.
	 * @param email del obrero que buscamos.
	 * @return obrero con ese email.
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM worker  WHERE worker.email LIKE ?1% AND worker.active = true", nativeQuery = true)
	Worker findByEmail(String email) throws IllegalArgumentException;
	
	/**
	 * Metodo que devuelve una lista de trabajadores activos.
	 * @param valor de actividad en la empresa(si/no).
	 * @return obreros activos/no activos.
	 * @throws IllegalArgumentExceptions
	 */
	@Query(value="SELECT * FROM worker WHERE worker.active = ?1", nativeQuery = true)
	List<Worker> findByActive(Boolean active) throws IllegalArgumentException;
}
