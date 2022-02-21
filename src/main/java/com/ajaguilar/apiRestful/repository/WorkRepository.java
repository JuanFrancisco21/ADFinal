package com.ajaguilar.apiRestful.repository;

import java.awt.Point;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ajaguilar.apiRestful.model.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{
		/**
		 * Método que devuelve una obra por un nombre.
		 * @param nombre de la obra que buscamos.
		 * @return obras con ese nombre.
		 * @throws IllegalArgumentException
		 */
		@Query(value="SELECT * FROM work  WHERE work.name LIKE ?1%", nativeQuery = true)
		Work findByName(String name) throws IllegalArgumentException;
		
		/**
		 * Método que devuelve las obras que tiene un Trabajador(chief).
		 * @param id del trabajador para devolver sus obras.
		 * @return lista de obras que tiene ese trabajador.
		 * @throws IllegalArgumentException.
		 */
		@Query(value="SELECT * FROM work  JOIN work.worker ON worker WHERE worker.id= ?1%", nativeQuery = true)
		List<Work> findWorkByWorker(Long idWorker) throws IllegalArgumentException;
		
		/**
		 * Método que devuelve una obra segun las coordendas.
		 * @param coordenadas de la obra.
		 * @return obra con las coordenadas introducidas.
		 * @throws IllegalArgumentException.
		 */
		@Query(value="SELECT * From work  WHERE work.location LIKE ?1", nativeQuery = true)
		Work findWorkByLocation(Point coordenadas) throws IllegalArgumentException;
		
		@Query(value="UPDATE work SET chief= ?1 WHERE work.id= ?2", nativeQuery = true)
		void updateChief(Long idworker, Long idwork) throws IllegalArgumentException;
		
}