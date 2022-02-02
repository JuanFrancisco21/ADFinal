package com.ajaguilar.apiRestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaguilar.apiRestful.model.Work;

public interface WorkRepository extends JpaRepository<Work, Long>{

}
