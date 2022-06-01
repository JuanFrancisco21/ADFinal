package com.ajaguilar.apiRestful.controller;

import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.services.DailylogService;
import com.ajaguilar.apiRestful.services.WorkerWorkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/dailylogs")
public class DailylogController {

	@Autowired
	DailylogService service;

	@Autowired
	WorkerWorkService wwservice;

	/**
	 * Metodo que devuelve una lista con todos los dailylogs de la BD
	 *
	 * @return una List con los dailylogs
	 */
	@ApiOperation(value = "Método que devuelve una lista con todos los dailylogs de la BD.", notes = "", tags = "getAllDailylogs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping
	public ResponseEntity<List<Dailylog>> getAllDailylogs() {
		try {
			List<Dailylog> result = service.getAllDailylogs();
			return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Metodo para obtener un dailylog concreto segun su id
	 *
	 * @param id del dailylog a devolver
	 * @return el objeto dailylog en caso de que este exista, un dailylog vacio en
	 *         caso contrario
	 */
	@ApiOperation(value = "Método para obtener un dailylog concreto según su id.", notes = "", tags = "getDailylogById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/{id}")
	public ResponseEntity<Dailylog> getDailylogById(@PathVariable("id") Long id) {
		try {
			Dailylog result = service.getDailylogbyId(id);
			return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Metodo que crea un dailylog en la BD
	 *
	 * @param log Dailylog a crear
	 * @return El dailylog creado si tiene �xito, dailylog vacio si falla
	 */
	@ApiOperation(value = "Método que crea un dailylog en la BD.", notes = "", tags = "createDailylog")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping("/add/{workerWorkid}")
	public ResponseEntity<Dailylog> createDailylog(@Valid @RequestBody Dailylog log,
			@PathVariable("workerWorkid") Long wwid) {
		if (log != null && log.getId() == -1) {
			try {
				try {
					log.setWorkerWork(wwservice.getWorkerWorkById(wwid));
				} catch (Exception e) {
					System.err.println("Error al obtener workerWork");
				}
				Dailylog result = service.createDailylog(log);
				if (log.getWorkerWork() != null && log.getWorkerWork().getId() != -1) {
					try {
						wwservice.updateWorkerWork(log.getWorkerWork());

					} catch (Exception e) {
						System.err.println("Error en workerWork update");
					}
				}

				return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo para editar un dailylog que ya exista en la BD
	 *
	 * @param log Dailylog a editar
	 * @return El objeto dailylog editado
	 */
	@ApiOperation(value = "Método para editar un dailylog que ya exista en la BD.", notes = "", tags = "updateDailylog")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PutMapping
	public ResponseEntity<Dailylog> updateDailylog(@Valid @RequestBody Dailylog log) {
		Dailylog result;
		try {
			result = service.updateDailylog(log);

			if (log.getWorkerWork() != null && log.getWorkerWork().getId() != -1) {
				try {
					wwservice.updateWorkerWork(log.getWorkerWork());

				} catch (Exception e) {
					System.err.println("Error en workerWork update");
				}
			}

			return new ResponseEntity<Dailylog>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Dailylog>(new Dailylog(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Metodo que elimina un dailylog de la BD segun su id
	 * 
	 * @param id ID del dailylog que se busca eliminar
	 * @return Status ok si el objeto es eliminado, Bad Request en caso contrario
	 */
	@ApiOperation(value = "Método que elimina un dailylog de la BD según su id", notes = "", tags = "deleteDailylogById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@DeleteMapping("/{id}")
	public HttpStatus deleteDailylogById(@PathVariable("id") Long id) {
		Dailylog log = service.getDailylogbyId(id);
		if (id > 0 && log != null) {
			try {
				service.deleteDailylog(id);
				if (log.getWorkerWork() != null && log.getWorkerWork().getId() != -1) {
					try {
						wwservice.updateWorkerWork(log.getWorkerWork());

					} catch (Exception e) {
						System.err.println("Error en workerWork update");
					}
				}
				return HttpStatus.OK;
			} catch (Exception e) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}

	/**
	 * Metodo que devuelve todos los dailylogs creados en una fecha concreta
	 * 
	 * @param date Fecha de los dailylogs
	 * @return Lista de los dailylogs que coincidan con dicha fecha
	 */
	@ApiOperation(value = "Método que devuelve todos los dailylogs creados en una fecha concreta.", notes = "", tags = "getDailylogsByDate")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/date/{date}")
	public ResponseEntity<List<Dailylog>> getDailylogsByDate(@PathVariable("date") String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		// convert String to LocalDate
		LocalDate localDate = LocalDate.parse(date, formatter);
		Date day = Date.valueOf(localDate);
		try {
			List<Dailylog> result = service.getDailylogsByDay(day);
			return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Devuelve todos los dailylogs de un workerWork concreto
	 * 
	 * @param idworkerWork ID del workerWork del que queremos obtener los dailylogs
	 * @return Lista de los dailylogs que coincidan con dicho workerWork
	 */
	@ApiOperation(value = "Devuelve todos los dailylogs de un workerWork concreto.", notes = "", tags = "getDailylogsByWorkerWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/workerWork/{idworkerWork}")
	public ResponseEntity<List<Dailylog>> getDailylogsByWorkerWork(@PathVariable("idworkerWork") Long idworkerWork) {
		try {
			List<Dailylog> result = service.getDailylogsByWorkerwork(idworkerWork);
			return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Devuelve todos los dailylogs de un trabajador concreto
	 * 
	 * @param idWorker ID del trabajador del que queremos obtener los dailylogs
	 * @return Lista de los dailylogs que coincidan con dicho trabajador
	 */
	@ApiOperation(value = "Devuelve todos los dailylogs de un trabajador concreto.", notes = "", tags = "getDailylogsByWorker")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/worker/{idWorker}")
	public ResponseEntity<List<Dailylog>> getDailylogsByWorker(@PathVariable("idWorker") Long idWorker) {
		try {
			List<Dailylog> result = service.getDailylogsByWorker(idWorker);
			return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Devuelve todos los dailylogs de una obra en concreto
	 * 
	 * @param idWork ID de la obra cuyos dailylogs queremos obtener
	 * @return Lista de los dailylogs que coincidan con la obra
	 */
	@ApiOperation(value = "Devuelve todos los dailylogs de una obra concreta.", notes = "", tags = "getDailylogsByWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/work/{idWork}")
	public ResponseEntity<List<Dailylog>> getDailylogsByWork(@PathVariable("idWork") Long idWork) {
		try {
			List<Dailylog> result = service.getDailylogsByWork(idWork);
			return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Devuelve todos los dailylogs de un mes en concreto
	 * 
	 * @param month: mes del cual cuyos dailylogs queremos obtener
	 * @return Lista de los dailylogs de ese mes
	 */
	@ApiOperation(value = "Devuelve todos los dailylogs de un mes.", notes = "", tags = "getDailylogsByMonth")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Dailylog.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/month/{month}")
	public ResponseEntity<List<Dailylog>> getDailylogsByWork(@PathVariable("month") int month) {
		if(month > 0 && month <= 12) {
			try {
				List<Dailylog> result = service.getDailylogsByMonth(month);
				return new ResponseEntity<List<Dailylog>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<List<Dailylog>>(new ArrayList<Dailylog>(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}
}