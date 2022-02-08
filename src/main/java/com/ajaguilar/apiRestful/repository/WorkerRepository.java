package com.ajaguilar.apiRestful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ajaguilar.apiRestful.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long>{
	/**
	 * Método que devuelve un trabajador por un nombre.
	 * @param nombre del obrero que buscamos.
	 * @return obrero con ese nombre.
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM worker WHERE worker.name LIKE %?1%", nativeQuery = true)
	Worker findByName(String name) throws IllegalArgumentException;
	
	/**
	 * Método que devuelve un trabajador por un apellido.
	 * @param apellido del obrero que buscamos.
	 * @return obrero con ese apellido.
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM worker  WHERE worker.surname LIKE %?1%", nativeQuery = true)
	Worker findBySurname(String surname) throws IllegalArgumentException;

	/**
	 * Método que devuelve una lista de trabajadores activos.
	 * @param valor de actividad en la empresa(si/no).
	 * @return obreros activos/no activos.
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM worker WHERE worker.active LIKE %?1%", nativeQuery = true)
	List<Worker> findByActive(Boolean active) throws IllegalArgumentException;
}
