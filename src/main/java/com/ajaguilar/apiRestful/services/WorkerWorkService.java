package com.ajaguilar.apiRestful.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.WorkerWork;
import com.ajaguilar.apiRestful.repository.WorkerWorkRepository;

@Service
public class WorkerWorkService {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkerWorkService.class);


	@Autowired
	WorkerWorkRepository repository;

	/**
	 * Método que nos proporciona todos los WorkerWork
	 * 
	 * @return Lista con todo los WorkerWork
	 */
	public List<WorkerWork> getAllWorkerWork() {
		List<WorkerWork> result = repository.findAll();
		if (!result.isEmpty()) {
			logger.info("Consulta exitosa en getAllWorkerWork");
			return result;
		} else {
			logger.error("Error--> NullPointerException al traer todas la relaciones, getAllWorkerWork");
			throw new NullPointerException("WorkerWork is empty");
		}

	}

	/**
	 * Método que proporciona un WorkerWork cuya id coincida con el valor introducido
	 * 
	 * @param id La id por la que se va a buscar en la base de datos
	 * @return El WorkerWork cuya id coincida
	 */
	public WorkerWork getWorkerWorkById(Long id) {
		if (id != null) {
			try {
				Optional<WorkerWork> result = repository.findById(id);
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerWorkById");
					return result.get();
				} else {
					logger.error("Error ---> La relación no existe", id + ", getWorkerWorkById");
					throw new RecordNotFoundException("WorkerWork with the following id has not been defined ", id);
				}
			} catch (Exception e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerWorkById :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al traer relacion mediante id, getWorkerWorkById");
			throw new NullPointerException("Null IDs are prohibited");
		}
	}

	/**
	 * Método que instancia un objeto WorkerWork en la base de datos
	 * 
	 * @param workerWork El objeto a almacenar
	 * @return Devuelve el workerwork creado
	 */
	public WorkerWork createWorkerWork(WorkerWork workerWork) {
		if (workerWork != null) {
			if (workerWork.getId() < 0) {
				try {
					logger.info("Consulta exitosa en createWorkerWork");
					return workerWork = repository.save(workerWork);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en createWorkerWork :" + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateWorkerWork(workerWork);
			}
		} else {
			logger.error("Error--> NullPointerException al crear relacion, createWorkerWork");
			throw new NullPointerException("The workerwork is null");
		}
	}

	/**
	 * Actualiza el workerwork introducido
	 * 
	 * @param workerWork El workerwork que va a ser actualizado
	 * @return El workerwork actualizado
	 */
	public WorkerWork updateWorkerWork(WorkerWork workerWork) {
		if (workerWork != null) {
			Optional<WorkerWork> result = Optional.of(getWorkerWorkById(workerWork.getId()));
			if (result != null) {
				if (result.isPresent()) {
					WorkerWork newWorkerWork = result.get();
					newWorkerWork.setId(workerWork.getId());
					newWorkerWork.setWorker(workerWork.getWorker());
					newWorkerWork.setWork(workerWork.getWork());
					newWorkerWork.setCurrent(workerWork.getCurrent());
					try {
						logger.info("Consulta exitosa en updateWorkerWork");
						return repository.save(newWorkerWork);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en updateWorkerWork :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> La relación no existe", workerWork + ", updateWorkerWork");
					throw new RecordNotFoundException("This workerwork doesn't exist", workerWork);
				}
			} else {
				logger.error("Error--> NullPointerException al actualizar relacion, updateWorkerWork");
				throw new NullPointerException("This workerwork doesn't exist");
			}
		} else {
			logger.error("Error--> NullPointerException al actualizar relacion, updateWorkerWork");
			throw new NullPointerException("Null workerworks are prohibited");
		}

	}
	
	/**
	 * Crea o actualiza el workerwork
	 * 
	 * @param workerWork El workerwork que se va a utilizar
	 * @return El workerwork que se ha creado o actualizado
	 */
	public WorkerWork createOrUpdateWorkerwork(WorkerWork workerWork) {
		if (workerWork != null) {
			if (workerWork.getId() > 0) {
				Optional<WorkerWork> result = repository.findById(workerWork.getId());
				if (result.isPresent()) {// UPDATE
					WorkerWork newWorkerWork = result.get();
					newWorkerWork.setId(workerWork.getId());
					newWorkerWork.setWorker(workerWork.getWorker());
					newWorkerWork.setWork(workerWork.getWork());
					newWorkerWork.setCurrent(workerWork.getCurrent());
					try {
						logger.info("Consulta exitosa en createOrUpdateWorkerwork");
						return repository.save(newWorkerWork);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en createOrUpdateWorkerwork :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {// INSERT
					workerWork.setId(null);
					try {
						return repository.save(workerWork);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en createOrUpdateWorkerwork :" + e);
						throw new IllegalArgumentException(e);
					}
				}
			} else {// INSERT
				try {
					return repository.save(workerWork);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en createOrUpdateWorkerwork :" + e);
					throw new IllegalArgumentException(e);
				}
			}
		} else {
			logger.error("Error--> NullPointerException al crear/actualizar relacion, createOrUpdateWorkerwork");
			throw new NullPointerException("Null workerworks are prohibited");
		}
	}

	/**
	 * Devuelve los workerwork que tengan al worker introducido
	 * 
	 * @param idWorker La id del worker
	 * @return Una lista con todos los workerwork que cumplan los requisitos
	 */
	public List<WorkerWork> getWorkerWorkByWorker(Long idWorker) {
		if (idWorker != null) {
			try {
				Optional<List<WorkerWork>> result = Optional.of(repository.findByWorker(idWorker));
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerWorkByWorker");
					return result.get();
				} else {
					logger.error("Error ---> La relación no existe", idWorker + ", getWorkerWorkByWorker");
					throw new RecordNotFoundException("This workerwork doesn't exist, idWorker= ", idWorker);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerWorkByWorker :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al buscar relacion, getWorkerWorkByWorker");
			throw new NullPointerException("idWorker is null");
		}

	}
	
	/**
	 * Devuelve el workerwork activo del worker introducido
	 * 
	 * @param idWorker La id del worker
	 * @return El workerwork que cumpla los requisitos
	 */
	public WorkerWork getWorkerWorkByCurrentWorker(Long idWorker) {
		if (idWorker != null) {
			try {
				Optional<WorkerWork> result = Optional.of(repository.findByCurrentWorker(idWorker));
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerWorkByCurrentWorker");
					return result.get();
				} else {
					logger.error("Error ---> La relación no existe", idWorker + ", getWorkerWorkByCurrentWorker");
					throw new RecordNotFoundException("This workerwork doesn't exist, idWorker= ", idWorker);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerWorkByCurrentWorker :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al buscar relacion, getWorkerWorkByCurrentWorker");
			throw new NullPointerException("idWorker is null");
		}

	}
	
	/**
	 * Devuelve los workerwork que tengan al work introducido
	 * 
	 * @param idWork La id del work
	 * @return Una lista con todos los workerwork que cumplan los requisitos
	 */
	public List<WorkerWork> getWorkerWorkByWork(Long idWork) {
		if (idWork != null) {
			try {
				Optional<List<WorkerWork>> result = Optional.of(repository.findByWork(idWork));
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerWorkByWork");
					return result.get();
				} else {
					logger.error("Error ---> La relación no existe", idWork + ", getWorkerWorkByWork");
					throw new RecordNotFoundException("This workerwork doesn't exist, idWork= ", idWork);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerWorkByWork :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al buscar relacion, getWorkerWorkByWork");
			throw new NullPointerException("idWork is null");
		}

	}

	/**
	 * Elimina el workerwork con la id introducida
	 * 
	 * @param id La id del workerwork
	 */
	public void deleteWorkerWorkById(Long id) {
		if (id != null) {
			Optional<WorkerWork> workwerWork = repository.findById(id);
			if (workwerWork != null) {
				if (workwerWork.isPresent()) {
					logger.info("Consulta exitosa en deleteWorkerWorkById");
					repository.deleteById(id);
				} else {
					logger.error("Error ---> La relacion no existe,"+id+" deleteWorkerWorkById");
					throw new RecordNotFoundException("Couldn't find workerwork with this id= ", id);
				}
			} else {
				logger.error("Error--> NullPointerException al borrar relacion, deleteWorkerWorkById");
				throw new NullPointerException("This workerwork doesn't exist");
			}
		} else {
			logger.error("Error--> NullPointerException al borrar relacion, deleteWorkerWorkById");
			throw new NullPointerException("Null workerworks are prohibited");
		}

	}

}
