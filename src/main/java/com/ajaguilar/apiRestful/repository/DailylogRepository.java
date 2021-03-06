package com.ajaguilar.apiRestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaguilar.apiRestful.model.Dailylog;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailylogRepository extends JpaRepository<Dailylog, Long>{

    /**
     * M�todo que devuelve una lista de dailylogs por una fecha.
     *
     * @param date: fecha del dailylog.
     * @return dailylogs con esa fecha.
     */
    @Query(value="SELECT d.* FROM dailylog d WHERE d.date LIKE ?1", nativeQuery = true)
    List<Dailylog> findByDate(Date date);
    
    
    /**
     * Método que devuelve la lista de dailylogs asociada a su WorkerWork
     * @param workerWorkId: id del workerwork
     * @return la lista de dailylogs de ese workerwork
     */
    @Query(value="SELECT d.* FROM dailylog d WHERE d.id_workerwork= ?1", nativeQuery = true)
    List<Dailylog> findByWorkerwork(Long workerWorkId);
    
    /**
     * Método que devuelve todos los dailylogs de un trabajador completo
     * @param workerId: ID del trabajador
     * @return Lista de los dailylogs de ese trabajador */
    @Query(value="SELECT d.* FROM dailylog d, worker_work w WHERE d.id_workerwork=w.id AND w.id_worker=?1", nativeQuery = true)
    List<Dailylog> findByWorker(Long workerId);
    
    /**
     * Método que devuelve todos los dailylogs de una obra concreta
     * @param workId: ID de la obra
     * @return Lista de los dailylogs de esa obra */
    @Query(value="SELECT d.* FROM dailylog d, worker_work w WHERE d.id_workerwork=w.id AND w.id_work=?1", nativeQuery = true)
    List<Dailylog> findByWork(Long workId);
    
    /**
	 * Método que devuelve los dailyogs de un mes
	 * @param month: el mes del que queremos obtener los registros
	 * @param year: el año
	 * @return Lista de los registros de ese mes */
	@Query(value="SELECT d.* FROM dailylog d WHERE EXTRACT(MONTH FROM date)=?1 AND EXTRACT(YEAR FROM date)=?2", nativeQuery = true)
	List<Dailylog> findByMonth(int month, int year);
	
	/**
	 * Método que devuelve los dailyogs de un trabajador en un mes
	 * @param month: el mes del que queremos obtener los registros
	 * @param year: el año
	 * @param idWorker: id del trabajador
	 * @return Lista de los registros de ese trabajador en ese mes */
	@Query(value="SELECT d.* FROM dailylog d, worker_work w, worker t WHERE EXTRACT(MONTH FROM date)=?1 AND EXTRACT(YEAR FROM date)=?2"
			+ " AND w.id_worker=t.id AND d.id_workerwork=w.id AND t.id=?3", nativeQuery = true)
	List<Dailylog> findByWorkerMonth(int month, int year, long idWorker);
	
	/**
	 * Método que devuelve los dailyogs de una obra en un mes concreto
	 * @param month: el mes del que queremos obtener los registros
	 * @param year: el año
	 * @param idWork: id de la obra
	 * @return Lista de los registros de esa obra en ese mes */
	@Query(value="SELECT d.* FROM dailylog d, worker_work w, work t WHERE EXTRACT(MONTH FROM date)=?1 AND EXTRACT(YEAR FROM date)=?2"
			+ " AND w.id_work=t.id AND d.id_workerwork=w.id AND t.id=?3", nativeQuery = true)
	List<Dailylog> findByWorkMonth(int month, int year, long idWork);
	
	
}
