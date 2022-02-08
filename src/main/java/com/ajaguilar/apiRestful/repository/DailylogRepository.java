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
     * Método que devuelve una lista de dailylogs por una fecha.
     *
     * @param fecha del dailylog.
     * @return dailylogs con esa fecha.
     */
    @Query(value="SELECT d FROM dailylog d WHERE dailylog.date LIKE %?1", nativeQuery = true)
    List<Dailylog> findByDate(Date date);
    
    
    //@Query(value="SELECT d FROM dailylog d JOIN dailylog.workerwork ON workerwork WHERE workerwork.id= ?1", nativeQuery = true)
    //List<Dailylog> findByWorkerwork(Long workerWorkId);
}
