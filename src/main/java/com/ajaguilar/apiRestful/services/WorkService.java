package com.ajaguilar.apiRestful.services;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.repository.WorkRepository;
import com.ajaguilar.apiRestful.repository.WorkerRepository;

@Service
public class WorkService {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);


	@Autowired
	WorkRepository repository;
	@Autowired
	WorkerRepository wrepository;

	/**
	 * M�todo para obtener todas las obras.
	 * 
	 * @return Lista con todas las obras de la BBDD.
	 */
	public List<Work> getAllWork() {
		List<Work> result = repository.findAll();
		if (!result.isEmpty()) {
			logger.info("Consulta exitosa en getAllWork");
			return result;
		} else {
			logger.error("Error--> NullPointerException al traer todas la obras, getAllWork");
			throw new NullPointerException("Error: Lista de obras vacia");
		}
	}

	/**
	 * M�todo para devolver una obra por su id.
	 * 
	 * @param id de la obra.
	 * @return Obra con el id introducido.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al obtener objeto nulo.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work getWorkById(Long id) throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Work> result = Optional.of(repository.findById(id).get());
				if (result.isPresent()) {
					logger.info("Consulta exitosa en getWorkById");
					return result.get();

				} else {
					logger.error("Error ---> La obra no existe", id + ", getWorkById");
					throw new RecordNotFoundException("La obra no existe con id:", id);
				}
			} catch (Exception e) {
				logger.error("Error ---> IllegarArgumentException en getWorkById :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error--> NullPointerException al traer obra mediante id, getWorkById");
			throw new NullPointerException("Error: Id introducido con valor nulo");
		}

	}

	/**
	 * M�todo para crear nueva obra, en caso de existir la actuliza.
	 * 
	 * @param Obra a crear/guardar en la base de datos.
	 * @return Obra un vez se cree/actualize.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work createWork(Work work) throws NullPointerException, IllegalArgumentException {
		if (work != null) {
			if (work.getId() < 0) {
				try {
					logger.info("Consulta exitosa en createWork");
					return work = repository.save(work);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en createWork :" + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateWork(work);
			}
		} else {
			logger.error("Error--> NullPointerException al crear obra, createWork");
			throw new NullPointerException("Error: La obra introducida tiene valor nulo");
		}
	}

	/**
	 * M�todo para la acualizaci�n de obras existentes en la BBDD.
	 * 
	 * @param Obra que se actulizar� en la BBDD.
	 * @return Obra actualizada.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work updateWork(Work work)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (work != null) {
			Optional<Work> result = Optional.of(getWorkById(work.getId()));
			if (result != null) {
				if (result.isPresent()) {
					Work newWork = result.get();
					newWork.setId(work.getId());
					newWork.setName(work.getName());
					newWork.setDescription(work.getDescription());
					newWork.setLocation(work.getLocation());
					
					try {
						logger.info("Consulta exitosa en updateWork");

						if(work.getChief().getId() > 0) {
							newWork.setChief(work.getChief());
							repository.updateChief(work.getChief().getId(), work.getId());
							return repository.save(newWork);
						}else {
							return repository.save(newWork);
						}
						
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en updateWork :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> La obra no existe", work.getId() + ", updateWork");
					throw new RecordNotFoundException("No existe la obra:", work);
				}
			} else {
				logger.error("Error--> NullPointerException al actualizar obra, updateWork");
				throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
			}
		} else {
			logger.error("Error--> NullPointerException al actualizar obra, updateWork");
			throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
		}

	}

	/**
	 * M�todo automatico para actualizar/crear una obra seg�n si esta ya creada.
	 * 
	 * @param Obra que se quiere acutalizar/crear.
	 * @return Obra la cual se a actualizado o en su defecto creada.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work createOrUpdateWork(Work work)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (work != null) {
			if (work.getId() > 0) {
				Optional<Work> result = repository.findById(work.getId());
				if (result.isPresent()) {// UPDATE
					Work newWork = result.get();
					newWork.setId(work.getId());
					newWork.setName(work.getName());
					newWork.setDescription(work.getDescription());
					newWork.setLocation(work.getLocation());
					newWork.setChief(work.getChief());
					newWork.setWorkerWork(work.getWorkerWork());
					try {
						logger.info("Consulta exitosa en createOrUpdateWork");

						return repository.save(newWork);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en createOrUpdateWork :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {// INSERT
					work.setId(null);
					try {
						return repository.save(work);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en createOrUpdateWork :" + e);
						throw new IllegalArgumentException(e);
					}
				}
			} else {// INSERT
				try {
					return repository.save(work);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en createOrUpdateWork :" + e);
					throw new IllegalArgumentException(e);
				}
			}
		} else {
			logger.error("Error--> NullPointerException al crear/updatear obra, createOrUpdateWork");
			throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
		}
	}
	
	/**
	 * M�todo para obtener una obra por su nombre.
	 * 
	 * @param Nombre de la obra a buscar.
	 * @return Obra con el nombre introducido.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work getWorkByName(String nombre) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(nombre != null) {
			try {
				Optional<Work> result = Optional.of(repository.findByName(nombre));
				if(result.isPresent()) {
					logger.info("Consulta exitosa en getWorkByName");

					return result.get();
				}else {
					logger.error("Error ---> La obra no existe, getWorkByName");
					throw new RecordNotFoundException("No existe la obra con Nombre:", nombre);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkByName :" + e);
				throw new IllegalArgumentException(e);
			}
			
		}else {
			logger.error("Error--> NullPointerException al traer obra mediante nombre, getWorkByName");
			throw new NullPointerException("Error: El nombre introducido tiene un valor nulo");
		}
	}
	
	/**
	 * M�todo para devolver la obras asignadas a un trabajador.
	 * 
	 * @param Trabajador al que esta asignada la obra.
	 * @return Lista de obras de un trabajador.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public List<Work> getWorkByWorker(Long idWorker) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(idWorker!=null) {
			try {
				Optional<List<Work>> lista = Optional.of(repository.findWorkByWorker(idWorker));
				if(lista.isPresent()) {
					logger.info("Consulta exitosa en getWorkByWorker");

					return lista.get();
				}else {
					logger.error("Error ---> La obra no existe, getWorkByWorker");
					throw new RecordNotFoundException("No existe obra con id_Worker:", idWorker);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkByWorker :" + e);
				throw new IllegalArgumentException(e);
			}						
		}else {
			logger.error("Error--> NullPointerException al traer obra mediante trabajador, getWorkByWorker");
			throw new NullPointerException ("Error: El usuario introducido tiene un valor nulo");
		}
			
	}
	
	/**
	 * M�todo para devolver una obra por sus coordenadas.
	 * @param Localizacion de la obra.
	 * @return Obra con la ubicaci�n introducida.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work getWorkByLocation(Point location) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(location!=null) {
			try {
				Optional<Work> result = Optional.of(repository.findWorkByLocation(location));
				if(result.isPresent()) {
					logger.info("Consulta exitosa en getWorkByLocation");

					return result.get();
				}else {
					logger.error("Error ---> La obra no existe, getWorkByLocation");
					throw new RecordNotFoundException("No existe obra con dicha localizaci�n", location);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getWorkByLocation :" + e);
				throw new IllegalArgumentException(e);
			}
		}else {
			logger.error("Error--> NullPointerException al traer obra por localizaci�n, getWorkByLocation");
			throw new NullPointerException ("Error: Las coordenadas introducidas tienen un valor nulo");
		}	
	}

	/**
	 * M�todo para el borrado de una obra, introduciendo su id.
	 * 
	 * @param id de la obra que queremos borrar.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public void deleteWorkById(Long id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			Optional<Work> result = repository.findById(id);
			if (result != null) {
				if (result.isPresent()) {
					logger.info("Eliminaci�n exitosa en getDeleteWorkById");

					Work found=result.get();
					found.setChief(null);
					repository.delete(found);
				} else {
					logger.error("Error ---> La obra no existe,"+id+" deleteWorkById");
					throw new RecordNotFoundException("No se puedo eliminar la obra con id:", id);
				}
			} else {
				logger.error("Error--> NullPointerException al borrar obra, deleteWorkById");
				throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
			}
		} else {
			logger.error("Error--> NullPointerException al borrar obra, deleteWorkById");
			throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
		}

	}
	
	

}
