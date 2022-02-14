package com.ajaguilar.apiRestful.controller;

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
import com.ajaguilar.apiRestful.services.WorkerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	 @ApiOperation(value = "Método para obtener una lista de todas los trabajadores de la BBDD."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping
	public ResponseEntity<List<Worker>> getAllWorker() {
		List<Worker> result = new ArrayList<Worker>();

		try {
			result = service.getAllWorker();
			return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Método obtener un trabajador mediante su id.
	 * 
	 * @param id del trabajador a a buscar.
	 * @return Trabajador encontrado por id. En caso de error devuelve un trabajador vacio.
	 */
	 @ApiOperation(value = "Método obtener un trabajador mediante su id."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/{id}")
	public ResponseEntity<Worker> getWorkerById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Worker result = service.getWorkerById(id);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
	 @ApiOperation(value = "Método para la creación de un nuevo trabajador."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping
	public ResponseEntity<Worker> createWorker(@Valid @RequestBody Worker worker) {
		if (worker != null && worker.getId() == -1) {
			try {
				Worker result = service.createWorker(worker);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método para la actulización de un trabajador.
	 * 
	 * @param Trabajador que se va a actualizar.
	 * @return Trabajador actualizado en la BBDD. En caso de error devuelve un trabajador vacio.
	 */
	 @ApiOperation(value = "Método para la actulización de un trabajador."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PutMapping
	public ResponseEntity<Worker> updateWorker(@Valid @RequestBody Worker worker) {
		if (worker != null && worker.getId() != -1) {
			try {
				Worker result = service.updateWorker(worker);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtener un trabajador mediante su nombre.
	 * 
	 * @param Nombre del trabajador a buscar.
	 * @return Trabajador encontrado por nombre. En caso de error devuelve un trabajador vacio.
	 */
	 @ApiOperation(value = "Método obtener un trabajador mediante su nombre."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/name/{name}")
	public ResponseEntity<Worker> getWorkerByName(@PathVariable("name") String name) {
		if (name != null && name.length()>1) {
			try {
				Worker result = service.getWorkerByName(name);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtener un trabajador mediante su apellido.
	 * 
	 * @param Apellido del trabajador a buscar.
	 * @return Trabajador encontrado por apellido. En caso de error devuelve un trabajador vacio.
	 */
	 @ApiOperation(value = "Método obtener un trabajador mediante su apellido."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/surname/{surname}")
	public ResponseEntity<Worker> getWorkerBySurname(@PathVariable("surname") String surname) {
		if (surname != null && surname.length()>1) {
			try {
				Worker result = service.getWorkerBySurname(surname);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Método obtener un trabajador por si esta activo/inactivo.
	 * 
	 * @param Activo/No activo atributo del trabajador.
	 * @return Lista de trabajadores activos/inactivos. En caso de error devuelve un lista vacia.
	 */
	 @ApiOperation(value = "Método obtener un trabajador por si esta activo/inactivo."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/active/{active}")
	public ResponseEntity<List<Worker>> getWorkerByActive(@PathVariable("active") Boolean active) {
		List<Worker> result = new ArrayList<Worker>();
		if (active != null) {
			try {
				result = service.getWorkerByActive(active);
				return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);		}
	}
	
	/**
	 * Método para borra un trabajador de la BBDD.
	 * 
	 * @param id del trabajador a borrar.
	 * @return Status ok si lo borra, Bad_request en caso de no borrarlo.
	 * @throws RecordNotFoundException Lanzado al no encontrar el valor.
	 */
	 @ApiOperation(value = "Método para borra un trabajador de la BBDD."
	            ,notes = "")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@DeleteMapping("/{id}")
	public HttpStatus deleteWorkerById(@PathVariable("id") Long id) throws RecordNotFoundException {
		if (id != null && id > -1) {
			try {
				service.deleteWorkerById(id);
				return HttpStatus.OK;
			} catch (Exception e) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
	
	
}
