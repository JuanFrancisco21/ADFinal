package com.ajaguilar.apiRestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaguilar.apiRestful.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long>{

}
