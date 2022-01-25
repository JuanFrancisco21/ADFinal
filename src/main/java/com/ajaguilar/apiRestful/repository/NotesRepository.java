package com.ajaguilar.apiRestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajaguilar.apiRestful.model.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long>{

}
