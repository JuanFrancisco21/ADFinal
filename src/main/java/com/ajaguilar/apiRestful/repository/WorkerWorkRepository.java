package com.ajaguilar.apiRestful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ajaguilar.apiRestful.model.WorkerWork;

public interface WorkerWorkRepository extends JpaRepository<WorkerWork, Long> {

	/**
	 * Método que devuelve los WorkerWork que contienen el trabajador especificado
	 * 
	 * @param idWorker La id del trabajador
	 * @return Una lista de los WorkerWork que contiene el trabajador especificado
	 * @throws IllegalArgumentException
	 */
	@Query(value = "SELECT * FROM worker_work WHERE worker_work.id_worker = ?1", nativeQuery = true)
	public List<WorkerWork> findByWorker(Long idWorker) throws IllegalArgumentException;

	/**
	 * Método que devuelve el WorkerWork que contiene el trabajador especificado y este es el actual
	 * 
	 * @param idWorker La id del trabajador
	 * @return El WorkerWork que contiene el trabajador y current es verdadero
	 * @throws IllegalArgumentException
	 */
	@Query(value = "SELECT * FROM worker_work WHERE worker_work.id_worker = ?1 AND workerWork.current = TRUE", nativeQuery = true)
	public WorkerWork findByCurrentWorker(Long idWorker) throws IllegalArgumentException;

	/**
	 * Método que devuelve los WorkerWork que contienen el trabajo especificado
	 * 
	 * @param idWork La id del trabajo
	 * @return Una lista de los WorkerWOrk que contiene el trabajo especificado
	 * @throws IllegalArgumentException
	 */
	@Query(value = "SELECT * FROM worker_work WHERE worker_work.id_work = ?1", nativeQuery = true)
	public List<WorkerWork> findByWork(Long idWork) throws IllegalArgumentException;

}
