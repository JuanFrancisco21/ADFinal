package com.ajaguilar.apiRestful.services;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.model.WorkerWork;
import com.ajaguilar.apiRestful.repository.DailylogRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailylogService {

    @Autowired
    DailylogRepository repository;
    
    //Returns all dailylogs from database
    public List<Dailylog> getAllDailylogs(){
        List<Dailylog> result = repository.findAll();
        return result;
    }
    
    //Returns a dailylog by his id
    public Dailylog getDailylogbyId(long id){
        Optional<Dailylog> optional;
        Dailylog result;
        optional = repository.findById(id);
        if(optional.isPresent()){
            result = optional.get();
        }else{
            throw new RecordNotFoundException("Dailylog not found", id);
        }
        return result;
    }
    
    //Creates a new dailylog
    public Dailylog createDailylog(Dailylog log){
        Dailylog result;
        result = repository.save(log);
        return result;
    }
    
    //Updates an existing dailylog getting his id
    public Dailylog updateDailylog(Dailylog log) throws Exception{
        Dailylog result = log;
        if (log.getId()!=null && log.getId()>0) {
			Optional<Dailylog> optional=repository.findById(log.getId());
			if (optional.isPresent()) {
				Dailylog newLog = optional.get();
				newLog.setId(log.getId());
                                newLog.setDate(log.getDate());
                                newLog.setHours(log.getHours());
                                newLog.setWorkerWork(log.getWorkerWork());
				newLog = repository.save(newLog);
				result = newLog;
			}else{
                            throw new Exception("Log not found");
                        }
		}else{
            throw new Exception("Log not found");
        }
        return result;
    }
    
    //Deletes an existing dailylog by his id
    public void deleteDailylog(Long id){
        Optional<Dailylog> log = repository.findById(id);
		if (log.isPresent()) {
			repository.deleteById(id);
		}else {
			throw new RecordNotFoundException("La nota no existe", id);
		}
    }
    
    //Returns all dailylogs from a day
    public List<Dailylog> getDailylogsByDay(Date day){
        List<Dailylog> result;
        result = repository.findByDate(day);
        return result;
    }
    
    //Returns all dailylogs from a concrete WorkerWork
    public List<Dailylog> getDailylogsByWorkerwork(WorkerWork ww){
        List<Dailylog> result;
        result = repository.findByWorkerwork(ww);
        return result;
    }
    
    
    
}
