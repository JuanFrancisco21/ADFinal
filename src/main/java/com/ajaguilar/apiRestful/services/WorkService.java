package com.ajaguilar.apiRestful.services;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.repository.WorkRepository;

@Service
public class WorkService {

	@Autowired
	WorkRepository repository;

	/**
	 * Método para obtener todas las obras.
	 * 
	 * @return Lista con todas las obras de la BBDD.
	 */
	public List<Work> getAllWork() {
		List<Work> result = repository.findAll();
		if (!result.isEmpty()) {
			return result;
		} else {
			throw new NullPointerException("Error: Lista de obras vacia");
		}
	}

	/**
	 * Método para devolver una obra por su id.
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
				Optional<Work> result = repository.findById(id);
				if (result.isPresent()) {
					return result.get();

				} else {
					throw new RecordNotFoundException("La obra no existe con id:", id);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error: Id introducido con valor nulo");
		}

	}

	/**
	 * Método para crear nueva obra, en caso de existir la actuliza.
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
					return work = repository.save(work);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateWork(work);
			}
		} else {
			throw new NullPointerException("Error: La obra introducida tiene valor nulo");
		}
	}

	/**
	 * Método para la acualización de obras existentes en la BBDD.
	 * 
	 * @param Obra que se actulizará en la BBDD.
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
				if (!result.isPresent()) {
					Work newWork = result.get();
					newWork.setId(work.getId());
					newWork.setName(work.getName());
					newWork.setDescription(work.getDescription());
					newWork.setLocation(work.getLocation());
					newWork.setChief(work.getChief());
					newWork.setWorkerWork(work.getWorkerWork());
					try {
						return repository.save(newWork);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("No existe la obra:", work);
				}
			} else {
				throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
		}

	}

	/**
	 * Método automatico para actualizar/crear una obra según si esta ya creada.
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
						return repository.save(newWork);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {// INSERT
					work.setId(null);
					try {
						return repository.save(work);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				}
			} else {// INSERT
				try {
					return repository.save(work);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			}
		} else {
			throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
		}
	}
	
	/**
	 * Método para obtener una obra por su nombre.
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
					return result.get();
				}else {
					throw new RecordNotFoundException("No existe la obra con Nombre:", nombre);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
			
		}else {
			throw new NullPointerException("Error: El nombre introducido tiene un valor nulo");
		}
	}
	
	/**
	 * Método para devolver la obras asignadas a un trabajador.
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
					return lista.get();
				}else {
					throw new RecordNotFoundException("No existe obra con id_Worker:", idWorker);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}						
		}else {
			throw new NullPointerException ("Error: El usuario introducido tiene un valor nulo");
		}
			
	}
	
	/**
	 * Método para devolver una obra por sus coordenadas.
	 * @param Localizacion de la obra.
	 * @return Obra con la ubicación introducida.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public Work getWorkByLocation(Point location) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(location!=null) {
			try {
				Optional<Work> obra = Optional.of(repository.findObraByLocation(location));
				if(obra.isPresent()) {
					return obra.get();
				}else {
					throw new RecordNotFoundException("No existe obra con dicha localización", location);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		}else {
			throw new NullPointerException ("Error: Las coordenadas introducidas tienen un valor nulo");
		}	
	}

	/**
	 * Método para el borrado de una obra, introduciendo su id.
	 * 
	 * @param id de la obra que queremos borrar.
	 * @throws RecordNotFoundException  Lanzado al no encontrar la obra.
	 * @throws NullPointerException     Lanzado al ser nula la obra recibida.
	 * @throws IllegalArgumentException Lanzado en caso de error.
	 */
	public void deleteWorkById(Long id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			Optional<Work> work = repository.findById(id);
			if (work != null) {
				if (work.isPresent()) {
					repository.deleteById(id);
				} else {
					throw new RecordNotFoundException("No se puedo eliminar la obra con id:", id);
				}
			} else {
				throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error: La obra introducida tiene un valor nulo");
		}

	}

}
