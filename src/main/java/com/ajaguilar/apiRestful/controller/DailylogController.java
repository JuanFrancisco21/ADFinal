package com.ajaguilar.apiRestful.controller;

import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.services.DailylogService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dailylogs")
public class DailylogController {

    @Autowired
    DailylogService service;
    
    /**
     * M�todo que devuelve una lista con todos los dailylogs de la BD
     * @return una List con los dailylogs
    */
    @GetMapping
    public ResponseEntity<List<Dailylog>> getAllDailylogs(){
        try{
            List<Dailylog> result = service.getAllDailylogs();
            return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * M�todo para obtener un dailylog concreto seg�n su id
     * @param id del dailylog a devolver
     * @return el objeto dailylog en caso de que este exista, un dailylog
     * vac?o en caso contrario
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dailylog> getDailylogById(@PathVariable("id") Long id){
        try{
            Dailylog result = service.getDailylogbyId(id);
            return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * M�todo que crea un dailylog en la BD
     * @param log Dailylog a crear
     * @return El dailylog creado si tiene �xito, dailylog vac�o si falla
     */
    @PostMapping
    public ResponseEntity<Dailylog> createDailylog(@Valid @RequestBody Dailylog log){
        if(log != null && log.getId()==-1){
            try{
                Dailylog result = service.createDailylog(log);
                return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * M�todo para editar un dailylog que ya exista en la BD
     * @param log Dailylog a editar
     * @return El objeto dailylog editado
     */
    @PutMapping
    public ResponseEntity<Dailylog> updateDailylog(@Valid @RequestBody Dailylog log){
        Dailylog result;
        try {
            result = service.updateDailylog(log);
            return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        
    }
    
    /**
     * M�todo que elimina un dailylog de la BD seg�n su id
     * @param id ID del dailylog que se busca eliminar
     * @return Status ok si el objeto es eliminado, Bad Request en caso contrario
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteDailylogById(@PathVariable("id") Long id){
        if(id > 0){
            try{
                service.deleteDailylog(id);
                return HttpStatus.OK;
            }catch(Exception e){
                return HttpStatus.NOT_FOUND;
            }
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    /**
     * M�todo que devuelve todos los dailylogs creados en una fecha concreta
     * @param date Fecha de los dailylogs
     * @return Lista de los dailylogs que coincidan con dicha fecha
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Dailylog>> getDailylogsByDate(@PathVariable("date") LocalDate date){
        Date day = Date.valueOf(date);
        try{
            List<Dailylog> result = service.getDailylogsByDay(day);
            return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        
    }
}    