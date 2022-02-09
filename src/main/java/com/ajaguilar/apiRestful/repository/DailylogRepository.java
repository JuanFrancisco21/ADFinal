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
     * @param fecha del dailylog.
     * @return dailylogs con esa fecha.
     */
    @Query(value="SELECT d.* FROM dailylog d WHERE d.date LIKE ?1", nativeQuery = true)
    List<Dailylog> findByDate(Date date);
    
    
    /**
     * Método que devuelve la lista de dailylogs asociada a su WorkerWork
     * @param workerWorkId id del workerwork
     * @return la lista de dailylogs de ese workerwork
     */
    @Query(value="SELECT d.* FROM dailylog d WHERE d.id_workerwork= ?1", nativeQuery = true)
    List<Dailylog> findByWorkerwork(Long workerWorkId);
}
