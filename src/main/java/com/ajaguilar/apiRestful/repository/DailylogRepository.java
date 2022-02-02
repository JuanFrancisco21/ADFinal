package com.ajaguilar.apiRestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaguilar.apiRestful.model.Dailylog;

public interface DailylogRepository extends JpaRepository<Dailylog, Long>{

}
