package com.ajaguilar.apiRestful.repository;

import java.awt.Point;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ajaguilar.apiRestful.model.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{
		/**
		 * Metodo que devuelve una obra por un nombre.
		 * @param nombre de la obra que buscamos.
		 * @return obras con ese nombre.
		 * @throws IllegalArgumentException
		 */
		@Query(value="SELECT * FROM work  WHERE work.name LIKE ?1% ", nativeQuery = true)
		Work findByName(String name) throws IllegalArgumentException;
		
		/**
		 * Metodo que devuelve las obras que tiene un Trabajador(chief).
		 * @param id del trabajador para devolver sus obras.
		 * @return lista de obras que tiene ese trabajador.
		 * @throws IllegalArgumentException.
		 */
		@Query(value="SELECT * FROM work  WHERE work.chief = ?1 AND work.active = true", nativeQuery = true)
		List<Work> findWorkByWorker(Long idWorker) throws IllegalArgumentException;
		
		/**
		 * Metodo que devuelve las obras que tiene un Trabajador(chief).
		 * @param id del trabajador para devolver sus obras.
		 * @return lista de obras que tiene ese trabajador.
		 * @throws IllegalArgumentException.
		 */
		@Query(value="SELECT * FROM work  WHERE work.chief = ?1 AND work.active = ?2 ", nativeQuery = true)
		List<Work> findActiveWorkByWorker(Long idWorker, boolean active) throws IllegalArgumentException;
		
		/**
		 * Metodo que devuelve una obra segun las coordendas.
		 * @param coordenadas de la obra.
		 * @return obra con las coordenadas introducidas.
		 * @throws IllegalArgumentException.
		 */
		@Query(value="SELECT * From work  WHERE work.location LIKE ?1 ", nativeQuery = true)
		Work findWorkByLocation(Point coordenadas) throws IllegalArgumentException;
		
		/**
		 * Metodo para actualizar encargado de un obra.
		 * @param idworker del encargado.
		 * @param idwork del trabajo.
		 * @throws IllegalArgumentException.
		 */
		@Modifying
		@Query(value="UPDATE work SET chief= ?1 WHERE work.id= ?2 ", nativeQuery = true)
		int updateChief(Long idworker, Long idwork) throws IllegalArgumentException;
		
		/**
		 * Metodo para actualizar encargado de un obra.
		 * @param idworker del encargado.
		 * @param idwork del trabajo.
		 * @return 
		 * @throws IllegalArgumentException.
		 */
		@Modifying
		@Query(value="UPDATE work SET active=false WHERE id=11 ",nativeQuery = true)
		int updateWork() throws IllegalArgumentException;
		
		/**
		 * Metodo que devuelve todas las obras activas.
		 * @return obras activas.
		 * @throws IllegalArgumentException
		 */
		@Query(value="SELECT * FROM work  WHERE work.active = true", nativeQuery = true)
		List<Work> findAllActive() throws IllegalArgumentException;
}