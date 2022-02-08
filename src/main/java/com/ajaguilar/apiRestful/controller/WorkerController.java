package com.ajaguilar.apiRestful.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.model.Worker;
import com.ajaguilar.apiRestful.services.WorkerService;

@RestController // Indicacion de que es una clase controller.
@RequestMapping("/worker") // Cuando se introduzca esta URL se ejecutara este controller.
public class WorkerController {

	@Autowired // Instancia service que ejecuta este controller.
	WorkerService service;
	
	/**
	 * Método para obtener una lista de todas los trabajadores de la BBDD.
	 * 
	 * @return Lista con todos los trabajadores de la BBDD. En caso de error devuelve una lista vacia.
	 */
	@GetMapping
	public ResponseEntity<List<Worker>> getAllWorker() {
		List<Worker> result = new ArrayList<Worker>();

		try {
			result = service.getAllWorker();
			return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Método obtener un trabajador mediante su id.
	 * 
	 * @param id del trabajador a a buscar.
	 * @return Trabajador encontrado por id. En caso de error devuelve un trabajador vacio.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Worker> getWorkerById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Worker result = service.getWorkerById(id);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método para la creación de un nuevo trabajador.
	 * 
	 * @param Trabajador que se va a crear.
	 * @return Trabajador creado en la BBDD. En caso de error devuelve un trabajador vacio.
	 */
	@PostMapping
	public ResponseEntity<Worker> createWorker(@Valid @RequestBody Worker worker) {
		if (worker != null && worker.getId() == -1) {
			try {
				Worker result = service.createWorker(worker);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
