package com.ajaguilar.apiRestful.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.WorkerWork;
import com.ajaguilar.apiRestful.repository.WorkerWorkRepository;

@Service
public class WorkerWorkService {

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
			return result;
		} else {
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
					return result.get();
				} else {
					throw new RecordNotFoundException("WorkerWork with the following id has not been defined ", id);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		} else {
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
					return workerWork = repository.save(workerWork);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateWorkerWork(workerWork);
			}
		} else {
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
						return repository.save(newWorkerWork);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("This workerwork doesn't exist", workerWork);
				}
			} else {
				throw new NullPointerException("This workerwork doesn't exist");
			}
		} else {
			throw new NullPointerException("Null workerworks are prohibited");
		}

	}
	
	/**
	 * Crea o actualiza el workerwork
	 * 
	 * @param workerWork El workerwork que se va a utilizar
	 * @return El workerwork que se ha creado o actualizado
	 */
	public WorkerWork createOrUpdateWork(WorkerWork workerWork) {
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
						return repository.save(newWorkerWork);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {// INSERT
					workerWork.setId(null);
					try {
						return repository.save(workerWork);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				}
			} else {// INSERT
				try {
					return repository.save(workerWork);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			}
		} else {
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
					return result.get();
				} else {
					throw new RecordNotFoundException("This workerwork doesn't exist, idWorker= ", idWorker);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
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
					return result.get();
				} else {
					throw new RecordNotFoundException("This workerwork doesn't exist, idWorker= ", idWorker);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
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
					return result.get();
				} else {
					throw new RecordNotFoundException("This workerwork doesn't exist, idWork= ", idWork);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
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
					repository.deleteById(id);
				} else {
					throw new RecordNotFoundException("Couldn't find workerwork with this id= ", id);
				}
			} else {
				throw new NullPointerException("This workerwork doesn't exist");
			}
		} else {
			throw new NullPointerException("Null workerworks are prohibited");
		}

	}

}
