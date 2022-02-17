package com.ajaguilar.apiRestful.services;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.repository.DailylogRepository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailylogService {

	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);

	@Autowired
	DailylogRepository repository;

	/**
	 * Método para obtener todos los dailylog.
	 * @return Lista con todos los dailylog.
	 */
	public List<Dailylog> getAllDailylogs() {
		List<Dailylog> result = repository.findAll();
		if (!result.isEmpty()) {
			logger.info("Consulta exitosa en getAllDailylogs");
			return result;
		} else {
			logger.error("Error--> NullPointerException al traer todas los dailylog, getAllDailylogs");
			throw new NullPointerException("Error: Lista de dailylog vacia");
		}
	}

	/**
	 * Método para obtener un dailylog por su id.
	 * @param id para buscar el dailylog.
	 * @return Dailylog con el id introducido.
	 */
	public Dailylog getDailylogbyId(Long id) {
		if (id != null) {
			try {
				Optional<Dailylog> result = Optional.of(repository.findById(id).get());
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getDailylogbyId");
					return result.get();

				} else {
					logger.error("Error ---> Dailylog no existente", id + ", getDailylogbyId");
					throw new RecordNotFoundException("La obra no existe con id:", id);
				}
			} catch (Exception e) {
				logger.error("Error ---> IllegarArgumentException en getDailylogbyId :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al traer dailylog mediante id, getDailylogbyId");
			throw new NullPointerException("Error: Id introducido con valor nulo");
		}
		
	}

	// Creates a new dailylog
	public Dailylog createDailylog(Dailylog log) {
		Dailylog result = null;
		if (log != null) {
			if (log.getId() < 0) {
				try {
					logger.info("Consulta exitosa en createDailylog");
					return result = repository.save(log);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en createDailylog :" + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				try {
					return updateDailylog(log);
				} catch (Exception e) {
					logger.error("Error ---> IllegarArgumentException en createDailylog :" + e);
					throw new IllegalArgumentException(e);
				}
			}
		} else {
			logger.error("Error--> NullPointerException al crear obra, createDailylog");
			throw new NullPointerException("Error: La obra introducida tiene valor nulo");
		}

	}

	// Updates an existing dailylog getting his id
	public Dailylog updateDailylog(Dailylog log) throws Exception {
		Dailylog result = log;
		if (log.getId() != null && log.getId() > 0) {
			Optional<Dailylog> optional = repository.findById(log.getId());
			if (optional.isPresent()) {
				Dailylog newLog = optional.get();
				newLog.setId(log.getId());
				newLog.setDate(log.getDate());
				newLog.setHours(log.getHours());
				newLog = repository.save(newLog);
				result = newLog;
			} else {
				throw new Exception("Log not found");
			}
		} else {
			throw new Exception("Log not found");
		}
		return result;
	}

	// Deletes an existing dailylog by his id
	public void deleteDailylog(Long id) {
		Optional<Dailylog> log = repository.findById(id);
		if (log.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("La nota no existe", id);
		}
	}

	// Returns all dailylogs from a day
	public List<Dailylog> getDailylogsByDay(Date day) {
		List<Dailylog> result;
		result = repository.findByDate(day);
		return result;
	}

	// Returns all dailylogs from a concrete WorkerWork
	public List<Dailylog> getDailylogsByWorkerwork(Long workerWorkId) {
		List<Dailylog> result;
		result = repository.findByWorkerwork(workerWorkId);
		return result;
	}

}
