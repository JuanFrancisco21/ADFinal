package com.ajaguilar.apiRestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.model.WorkerWork;
import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DailylogRepository extends JpaRepository<Dailylog, Long>{

    List<Dailylog> findByDate(Date date);
    List<Dailylog> findByWorkerwork(WorkerWork ww);
}
