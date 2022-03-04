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
import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.model.WorkerWork;
import com.ajaguilar.apiRestful.services.WorkerWorkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/workerWork")
public class WorkerWorkController {

	@Autowired
	WorkerWorkService service;

	/**
	 * Obtiene todos loas workerwork de la base de datos
	 * 
	 * @return Una lista con todos los workerwork
	 */
	@ApiOperation(value = "Método que devuelve todas las relaciones existentes.", notes = "", tags = "getAllWorkerWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada.") })
	@GetMapping
	public ResponseEntity<List<WorkerWork>> getAllWorkerWork() {
		List<WorkerWork> result = new ArrayList<WorkerWork>();

		try {
			result = service.getAllWorkerWork();
			return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Obtiene el workerwork cuya id coincida con la incluida
	 * 
	 * @param id La id del workerwork
	 * @return El workerwork cuya id coincida
	 */
	@ApiOperation(value = "Método que devuelve worker_work por el id", notes = "", tags = "getWorkerWorkById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@GetMapping("/{id}")
	public ResponseEntity<WorkerWork> getWorkerWorkById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				WorkerWork result = service.getWorkerWorkById(id);
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Crea un workerwork en la base de datos
	 * 
	 * @param workerWork El workerwork a ser introducido en la base de datos
	 * @return El workerwork introducido
	 */
	@ApiOperation(value = "Método de creacion de worker_work ", notes = "", tags = "createWorkerWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@PostMapping
	public ResponseEntity<WorkerWork> createWorkerWork(@Valid @RequestBody WorkerWork workerWork) {
		if (workerWork != null && workerWork.getId() == -1) {
			try {
				WorkerWork result = service.createWorkerWork(workerWork);
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Actualiza un workerwork
	 * 
	 * @param workerWork El workerwork a ser actualizado
	 * @return El workerwork actualizado
	 */
	@ApiOperation(value = "Método para actualizar worker_work", notes = "", tags = "updateWorkerWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@PutMapping
	public ResponseEntity<WorkerWork> updateWorkerWork(@Valid @RequestBody WorkerWork workerWork) {
		if (workerWork != null && workerWork.getId() != -1) {
			try {
				WorkerWork result = service.updateWorkerWork(workerWork);
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Crea o actualiza un workerwork
	 * 
	 * @param workerWork El workerwork a ser creado o actualizado
	 * @return El workerwork creado o actualizado
	 */
	public ResponseEntity<WorkerWork> createOrUpdateWorkerWork(@Valid @RequestBody WorkerWork workerWork) {
		if (workerWork != null && workerWork.getId() != -1) {
			try {
				WorkerWork result = service.createOrUpdateWorkerwork(workerWork);
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene los workerwork que tengan un worker específico
	 * 
	 * @param idWorker La id del worker
	 * @return Una lista con los workerworks que cumplan los requisitos
	 */
	@ApiOperation(value = "Método para buscar worker_work mediante el trabajador", notes = "", tags = "getWorkerWorkByWorker")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@GetMapping("idWorker/{idWorker}")
	public ResponseEntity<List<WorkerWork>> getWorkerWorkByWorker(@PathVariable("idWorker") Long idWorker) {
		List<WorkerWork> result = new ArrayList<WorkerWork>();
		if (idWorker != null && idWorker != -1) {
			try {
				result = service.getWorkerWorkByWorker(idWorker);
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Devuelve el workerwork actual de un worker
	 * 
	 * @param idWorker La id del worker
	 * @return El workerwork que cumple los requisitos
	 */
	@ApiOperation(value = "Método para buscar worker_work mediante el id del trabajador", notes = "", tags = "getWorkerWorkByWorker")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@GetMapping("/current/{idWorker}")
	public ResponseEntity<WorkerWork> getWorkerWorkByCurrentWorker(@PathVariable("idWorker") Long idWorker) {
		WorkerWork result = new WorkerWork();
		if (idWorker != null && idWorker != -1) {
			try {
				result = service.getWorkerWorkByCurrentWorker(idWorker);
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Devuelve una lista de workerwor cuyo work sea el introducido
	 * 
	 * @param idWork La id del work
	 * @return La lista de workerworks que cumple las condiciones
	 */
	@ApiOperation(value = "Método para buscar worker_work mediante el id del trabajo", notes = "", tags = "getWorkerWorkByWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@GetMapping("idWork/{idWork}")
	public ResponseEntity<List<WorkerWork>> getWorkerWorkByWork(@PathVariable("idWork") Long idWork) {
		List<WorkerWork> result = new ArrayList<WorkerWork>();
		if (idWork != null && idWork != -1) {
			try {
				result = service.getWorkerWorkByWork(idWork);
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Devuelve una lista de workerwor cuyo work sea el introducido y este activo
	 * 
	 * @param idWork La id del work activo
	 * @return La lista de workerworks que cumple las condiciones
	 */
	@ApiOperation(value = "Método para buscar worker_work mediante el id del trabajo", notes = "", tags = "getWorkerWorkByWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@GetMapping("idCurrentWork/{idWork}")
	public ResponseEntity<List<WorkerWork>> getWorkerWorkByCurrentWork(@PathVariable("idWork") Long idWork) {
		List<WorkerWork> result = new ArrayList<WorkerWork>();
		if (idWork != null && idWork != -1) {
			try {
				result = service.getWorkerWorkByCurrentWork(idWork);
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un workerwork en base a su id
	 * 
	 * @param id La id del workerwork
	 * @return OK en caso de borrarlo, BAD_REQUEST en caso de que no
	 * @throws RecordNotFoundException Si no encuentra el workerwork
	 */
	@ApiOperation(value = "Método para borrar worker_work por id", notes = "", tags = "deleteWorkerWorkById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 404, message = "Not found. Informacion no encontrada."),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class) })
	@DeleteMapping("/{id}")
	public HttpStatus deleteWorkerWorkById(@PathVariable("id") Long id) throws RecordNotFoundException {
		if (id != null && id > -1) {
			try {
				service.deleteWorkerWorkById(id);
			} catch (Exception e) {
				return HttpStatus.NOT_FOUND;
			}
			return HttpStatus.OK;
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
}
