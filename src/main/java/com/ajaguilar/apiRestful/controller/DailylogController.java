package com.ajaguilar.apiRestful.controller;

import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.services.DailylogService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dailylogs")
public class DailylogController {

    @Autowired
    DailylogService service;
    
    @GetMapping
    public ResponseEntity<List<Dailylog>> getAllDailylogs(){
        List<Dailylog> result = service.getAllDailylogs();
        return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Dailylog> getDailylogById(@PathVariable("id") Long id){
        Dailylog result = service.getDailylogbyId(id);
        return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Dailylog> createDailylog(Dailylog log){
        Dailylog result = service.createDailylog(log);
        return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<Dailylog> updateDailylog(Dailylog log){
        Dailylog result = new Dailylog();
        try {
            result = service.updateDailylog(log);
        } catch (Exception ex) {
            Logger.getLogger(DailylogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public HttpStatus deleteDailylogById(@PathVariable("id") Long id){
        service.deleteDailylog(id);
        return HttpStatus.OK;
    }
    
    @GetMapping("/{date}")
    public ResponseEntity<List<Dailylog>> getDailylogsByDate(@PathVariable("date") LocalDate date){
        Date day = Date.valueOf(date);
        List<Dailylog> result = service.getDailylogsByDay(day);
        return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
    }    
}
