package com.ajaguilar.apiRestful.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Worker;
import com.ajaguilar.apiRestful.repository.WorkerRepository;

@Service
public class WorkerService {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkerService.class);


	@Autowired
	WorkerRepository repository;
	
	/**
	 * Método para obtener todos los trabajadores.
	 * 
	 * @return Lista con todos los trabajadores de la BBDD.
	 */
	public List<Worker> getAllWorker() {
		List<Worker> result = repository.findAll();
		if (!result.isEmpty()) {
			logger.info("Consulta exitosa en getAllWorker");
			return result;
		} else {
			logger.error("Error--> NullPointerException al traer todos los trabajadores, getAllWorker");
			throw new NullPointerException("Error: Lista de trabajadores vacia");
		}
	}
	
	/**
	 * Método para devolver un trabajador por su id.
	 * 
	 * @param id del trabajador.
	 * @return Trabajador con el id introducido.
	 * @throws RecordNotFoundException  Lanzado al no encontrar el trabajador.
	 * @throws NullPointerException     Lanzado al obtener objeto nulo.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Worker getWorkerById(Long id) throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Worker> result = Optional.of(repository.findById(id).get());
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerById");
					return result.get();
				} else {
					logger.error("Error ---> El trabajador no existe", id + ", getWorkerById");
					throw new RecordNotFoundException("El trabajador no existe con id:", id);
				}
			} catch (Exception e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerById :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al traer trabajador mediante id, getWorkerById");
			throw new NullPointerException("Error: Id introducido con valor nulo");
		}

	}
	/**
	 * Método para crear nuevo trabajador, en caso de existir lo actuliza.
	 * 
	 * @param Trabajador a crear/guardar en la base de datos.
	 * @return Trabajador un vez se cree/actualize.
	 * @throws NullPointerException     Lanzado al ser nulo el trabajador recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Worker createWorker(Worker worker) throws NullPointerException, IllegalArgumentException {
		if (worker != null) {
			if (worker.getId() < 0) {
				try {
					logger.info("Consulta exitosa en createWorker");
					return worker = repository.save(worker);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en createWorker :" + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateWorker(worker);
			}
		} else {
			logger.error("Error--> NullPointerException al crear trabajador, createWorker");
			throw new NullPointerException("Error: Trabajador introducido tiene valor nulo");
		}
	}
	
	
	/**
	 * Método para la acualización de trabajadores existentes en la BBDD.
	 * 
	 * @param Trabajador que se actulizará en la BBDD.
	 * @return Trabajador actualizado.
	 * @throws RecordNotFoundException  Lanzado al no encontrar el trabajador.
	 * @throws NullPointerException     Lanzado al ser nulo el trabajador recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Worker updateWorker(Worker worker)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (worker != null) {
			Optional<Worker> result = Optional.of(getWorkerById(worker.getId()));
			if (result != null) {
				if (result.isPresent()) {
					Worker newWorker = result.get();
					newWorker.setId(worker.getId());
					newWorker.setName(worker.getName());
					newWorker.setSurname(worker.getSurname());
					newWorker.setActive(worker.getActive());
					newWorker.setPicture(worker.getPicture());
					try {
						logger.info("Consulta exitosa en updateWorker");
						return repository.save(newWorker);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en updateWorker :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> El trabajador no existe", worker.getId() + ", updateWorker");
					throw new RecordNotFoundException("No existe el trabajador:", worker);
				}
			} else {
				logger.error("Error--> NullPointerException al actualizar trabajador, updateWorker");
				throw new NullPointerException("Error: Trabajador introducido tiene un valor nulo");
			}
		} else {
			logger.error("Error--> NullPointerException al actualizar trabajador, updateWorker");
			throw new NullPointerException("Error: Trabajador introducido tiene un valor nulo");
		}

	}
	
	/**
	 * Método para obtener un trabajador por su nombre.
	 * 
	 * @param Nombre del trabajador a buscar en la BBDD.
	 * @return Trabajador con el nombre introducido. 
	 * @throws RecordNotFoundException  Lanzado al no encontrar el trabajador.
	 * @throws NullPointerException     Lanzado al ser nulo el trabajador recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Worker getWorkerByName(String nombre) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(nombre != null) {
			try {
				Optional<Worker> result = Optional.of(repository.findByName(nombre));
				if(result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerByName");
					return result.get();
				}else {
					logger.error("Error ---> El trabajador no existe", nombre + ", getWorkerByName");
					throw new RecordNotFoundException("No existe el trabajador con Nombre:", nombre);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerByName :" + e);
				throw new IllegalArgumentException(e);
			}
			
		}else {
			logger.error("Error--> NullPointerException al buscar trabajador, getWorkerByName");
			throw new NullPointerException("Error: Trabajador introducido tiene un valor nulo");
		}
	}
	
	/**
	 * Método para obtener un trabajador por su apellido.
	 * 
	 * @param Apellido del trabajador a buscar en la BBDD.
	 * @return Trabajador con el apellido introducido. 
	 * @throws RecordNotFoundException  Lanzado al no encontrar el trabajador.
	 * @throws NullPointerException     Lanzado al ser nulo el trabajador recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Worker getWorkerBySurname(String apellido) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(apellido != null) {
			try {
				System.out.println(apellido);
				Optional<Worker> result = Optional.of(repository.findBySurname(apellido));
				if(result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerBySurname");
					return result.get();
				}else {
					logger.error("Error ---> El trabajador no existe", apellido + ", getWorkerBySurname");
					throw new RecordNotFoundException("No existe el trabajador con Apellido:", apellido);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerBySurname :" + e);
				throw new IllegalArgumentException(e);
			}
			
		}else {
			logger.error("Error--> NullPointerException al buscar trabajador, getWorkerBySurname");
			throw new NullPointerException("Error: Trabajador introducido tiene un valor nulo");
		}
	}
	
	/**
	 * Método para devolver una lista trabajadores segun si esta activo.
	 * @param Booleano para indiar que trabajadores buscar.
	 * @return Trabajador con booleano introducido.
	 * @throws RecordNotFoundException  Lanzado al no encontrar el trabajador.
	 * @throws NullPointerException     Lanzado al ser nulo el trabajador recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public List<Worker> getWorkerByActive(Boolean active) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(active!=null) {
			try {
				Optional<List<Worker>> result = Optional.of(repository.findByActive(active));
				if(result.isPresent()) {
					logger.info("Consulta exitosa en getWorkerByActive");
					return result.get();
				}else {
					logger.error("Error ---> El trabajador no existe", active + ", getWorkerByActive");
					throw new RecordNotFoundException("No existen trabajadores con actividad:", active);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkerByActive :" + e);
				throw new IllegalArgumentException(e);
			}
		}else {
			logger.error("Error--> NullPointerException al buscar trabajador, getWorkerByActive");
			throw new NullPointerException ("Error: Booleano introducido tiene un valor nulo");
		}	
	}
	
	/**
	 * Método para el borrado de un trabajador, introduciendo su id.
	 * 
	 * @param id del trabajador que queremos borrar.
	 * @throws RecordNotFoundException  Lanzado al no encontrar el trabajador.
	 * @throws NullPointerException     Lanzado al ser nulo el trabajador recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public void deleteWorkerById(Long id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			Optional<Worker> result = repository.findById(id);
			if (result != null) {
				if (result.isPresent()) {
					repository.deleteById(id);
					logger.info("Consulta exitosa en deleteWorkerById");
				} else {
					logger.error("Error ---> El trabajador no existe", id + ", deleteWorkerById");
					throw new RecordNotFoundException("No se puedo eliminar el trabajador con id:", id);
				}
			} else {
				logger.error("Error--> NullPointerException al borrar trabajador, deleteWorkerById");
				throw new NullPointerException("Error: Trabajador introducido tiene un valor nulo");
			}
		} else {
			logger.error("Error--> NullPointerException al borrar trabajador, deleteWorkerById");
			throw new NullPointerException("Error: Trabajador introducido tiene un valor nulo");
		}

	}
	
}
