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
import com.ajaguilar.apiRestful.services.WorkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController // Indicacion de que es una clase controller.
@RequestMapping("/work") // Cuando se introduzca esta URL se ejecutara este controller.
public class WorkController {
	

	@Autowired // Instancia service que ejecuta este controller.
	WorkService service;

	/**
	 * M�todo para obtener una lista de todas las obras de la BBDD.
	 * 
	 * @return Lista con todas las obras de la BBDD. En caso de error devuelve una lista vacia.
	 */
	 @ApiOperation(value = "Método para obtener una lista de todas las obras de la BBDD."
	            ,notes = "", tags = "getAllWork")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping
	public ResponseEntity<List<Work>> getAllWork() {
		List<Work> result = new ArrayList<Work>();
		try {
			result = service.getAllWork();
			return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * M�todo obtener una obra mediante su id.
	 * 
	 * @param id de la obra a buscar.
	 * @return Obra encontrado por id. En caso de error devuelve una obra vacia.
	 */
	 @ApiOperation(value = "Método obtener una obra mediante su id."
	            ,notes = "", tags = "getWorkById")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/{id}")
	public ResponseEntity<Work> getWorkById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Work result = service.getWorkById(id);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * M�todo para la creaci�n de una nueva obra.
	 * 
	 * @param Obra que se va a crear.
	 * @return Obra creada en la BBDD. En caso de error devuelve una obra vacia.
	 */
	 @ApiOperation(value = "Método para la creación de una nueva obra."
	            ,notes = "", tags = "createWork")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping
	public ResponseEntity<Work> createWork(@Valid @RequestBody Work work) {
		if (work != null && work.getId() == -1) {
			try {
				Work result = service.createWork(work);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * M�todo para la actulizaci�n de una obra.
	 * 
	 * @param Obra que se va a actualizar.
	 * @return Obra actualizada en la BBDD. En caso de error devuelve una obra vacia.
	 */
	 @ApiOperation(value = "Método para la actulización de una obra."
	            ,notes = "", tags = "updateWork")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PutMapping
	public ResponseEntity<Work> updateWork(@Valid @RequestBody Work work) {
		if (work != null && work.getId() != -1) {
			try {
				Work result = service.updateWork(work);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * M�todo obtener una obra mediante su nombre.
	 * 
	 * @param Nombre de la obra a buscar.
	 * @return Obra encontrado por nombre. En caso de error devuelve una obra vacia.
	 */
	 @ApiOperation(value = "Método obtener una obra mediante su nombre."
	            ,notes = "", tags = "getWorkByName")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/name/{name}")
	public ResponseEntity<Work> getWorkByName(@PathVariable("name") String name) {
		if (name != null && name.length()>1) {
			try {
				Work result = service.getWorkByName(name);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Work>(new Work(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * M�todo obtener una obra mediante su trabajador.
	 * 
	 * @param Id del trabajador de la obra a buscar.
	 * @return Obras encontradas por id del trabajador. En caso de error devuelve una lista de obras vacia.
	 */
	 @ApiOperation(value = "Método obtener una obra mediante su trabajador."
	            ,notes = "", tags = "getWorkByWorker")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/idworker/{idworker}")
	public ResponseEntity<List<Work>> getWorkByWorker(@PathVariable("idWorker") Long idWorker) {
		List<Work> result = new ArrayList<Work>();
		if (idWorker != null && idWorker != -1) {
			try {
				result = service.getWorkByWorker(idWorker);
				return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<List<Work>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);		}
	}
	
	/**
	 * M�todo obtener una obra mediante su localizaci�n.
	 * 
	 * @param Localizaci�n de la obra a buscar.
	 * @return Obra encontradas por la localizaci�n. En caso de error devuelve una obra vacia.
	 */
	 @ApiOperation(value = "Método obtener una obra mediante su localización."
	            ,notes = "", tags = "getWorkByLocation")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/location/{location}")
	public ResponseEntity<Work> getWorkByLocation(@PathVariable("location") Point location) {
		Work result = new Work();
		if (location != null) {
			try {
				result = service.getWorkByLocation(location);
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Work>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);		}
	}

	/**
	 * M�todo para borra una obra de la BBDD.
	 * 
	 * @param id de la obra a borrar.
	 * @return Status ok si lo borra, Bad_request en caso de no borrarla.
	 * @throws RecordNotFoundException Lanzado al no encontrar el valor.
	 */
	 @ApiOperation(value = "Método para borrar una obra de la BBDD."
	            ,notes = "", tags = "deleteWorkById")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Work.class),
	            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
	            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@DeleteMapping("/{id}")
	public HttpStatus deleteWorkById(@PathVariable("id") Long id) throws RecordNotFoundException {
		if (id != null && id > -1) {
			try {
				service.deleteWorkById(id);
				return HttpStatus.OK;
			} catch (Exception e) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
	
}
