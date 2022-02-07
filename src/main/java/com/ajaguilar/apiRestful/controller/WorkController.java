package com.ajaguilar.apiRestful.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.model.Worker;
import com.ajaguilar.apiRestful.services.WorkService;

@RestController // Indicacion de que es una clase controller.
@RequestMapping("/work") // Cuando se introduzca esta URL se ejecutara este controller.
public class WorkController {

	@Autowired // Instancia service que ejecuta este controller.
	WorkService service;

	/**
	 * Método para obtener una lista de todas las obras de la BBDD.
	 * 
	 * @return Lista con todas las obras de la BBDD. En caso de error devuelve una lista vacia.
	 */
	@GetMapping
	public ResponseEntity<List<Work>> getAllWork() {
		List<Work> result = new ArrayList<Work>();

		try {
			result = service.getAllWork();
			return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Método obtener una obra mediante su id.
	 * 
	 * @param id de la obra a buscar.
	 * @return Obra encontrado por id. En caso de error devuelve una obra vacia.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Work> getWorkById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Work result = service.getWorkById(id);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método para la creación de una nueva obra.
	 * 
	 * @param Obra que se va a crear.
	 * @return Obra creada en la BBDD. En caso de error devuelve una obra vacia.
	 */
	@PostMapping
	public ResponseEntity<Work> createWork(@Valid @RequestBody Work work) {
		if (work != null && work.getId() == -1) {
			try {
				Work result = service.createWork(work);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método para la actulización de una obra.
	 * 
	 * @param Obra que se va a actualizar.
	 * @return Obra actualizada en la BBDD. En caso de error devuelve una obra vacia.
	 */
	@PutMapping
	public ResponseEntity<Work> updateWork(@Valid @RequestBody Work work) {
		if (work != null && work.getId() != -1) {
			try {
				Work result = service.updateWork(work);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método para la actulización de una obra o en caso de no existir la crea.
	 * 
	 * @param Obra que se va a actualizar/crear.
	 * @return Obra actualizada/creada en la BBDD. En caso de error devuelve una obra vacia.
	 */
	public ResponseEntity<Work> createOrUpdateWork(@Valid @RequestBody Work work) {
		if (work != null && work.getId() != -1) {
			try {
				Work result = service.createOrUpdateWork(work);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtener una obra mediante su nombre.
	 * 
	 * @param Nombre de la obra a buscar.
	 * @return Obra encontrado por nombre. En caso de error devuelve una obra vacia.
	 */
	@GetMapping("/{name}")
	public ResponseEntity<Work> getWorkByName(@PathVariable("name") String name) {
		if (name != null && name.length()>1) {
			try {
				Work result = service.getWorkByName(name);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtener una obra mediante su trabajador.
	 * 
	 * @param Id del trabajador de la obra a buscar.
	 * @return Obras encontradas por id del trabajador. En caso de error devuelve una lista de obras vacia.
	 */
	@GetMapping("/{idworker}")
	public ResponseEntity<List<Work>> getWorkByWorker(@PathVariable("idWorker") Long idWorker) {
		List<Work> result = new ArrayList<Work>();
		if (idWorker != null && idWorker != -1) {
			try {
				result = service.getWorkByWorker(idWorker);
				return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);		}
	}
	
	/**
	 * Método obtener una obra mediante su localización.
	 * 
	 * @param Localización de la obra a buscar.
	 * @return Obra encontradas por la localización. En caso de error devuelve una obra vacia.
	 */
	@GetMapping("/{location}")
	public ResponseEntity<Work> getWorkByLocation(@PathVariable("location") Point location) {
		Work result = new Work();
		if (location != null) {
			try {
				result = service.getWorkByLocation(location);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);		}
	}

	/**
	 * Método para borra una obra de la BBDD.
	 * 
	 * @param id de la obra a borrar.
	 * @return Status ok si lo borra, Bad_request en caso de no borrarla.
	 * @throws RecordNotFoundException Lanzado al no encontrar el valor.
	 */
	@DeleteMapping("/{id}")
	public HttpStatus deleteWorkById(@PathVariable("id") Long id) throws RecordNotFoundException {
		if (id != null && id > -1) {
			try {
				service.deleteWorkById(id);
			} catch (Exception e) {
				return HttpStatus.BAD_REQUEST;
			}
			return HttpStatus.OK;
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
	
}
