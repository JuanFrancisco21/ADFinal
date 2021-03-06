package com.ajaguilar.apiRestful.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Dailylog;
import com.ajaguilar.apiRestful.model.Work;
import com.ajaguilar.apiRestful.model.Worker;
import com.ajaguilar.apiRestful.model.WorkerWork;
import com.ajaguilar.apiRestful.services.DriveService;
import com.ajaguilar.apiRestful.services.FileService;
import com.ajaguilar.apiRestful.services.WorkService;
import com.ajaguilar.apiRestful.services.WorkerService;
import com.ajaguilar.apiRestful.services.WorkerWorkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*",methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController // Indicacion de que es una clase controller.
@RequestMapping("/worker") // Cuando se introduzca esta URL se ejecutara este controller.
public class WorkerController {

	@Autowired // Instancia service que ejecuta este controller.

	WorkerService service;
	@Autowired
	WorkerWorkService wwservice;
	@Autowired
	WorkService wservice;
	@Autowired
    FileService fileUploadService;

	/**
	 * Metodo para obtener una lista de todas los trabajadores de la BBDD.
	 * 
	 * @return Lista con todos los trabajadores de la BBDD. En caso de error
	 *         devuelve una lista vacia.
	 */
	@ApiOperation(value = "M??todo para obtener una lista de todas los trabajadores de la BBDD.", notes = "", tags = "getAllWorker")
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
	 * Metodo obtener un trabajador mediante su id.
	 * 
	 * @param id del trabajador a a buscar.
	 * @return Trabajador encontrado por id. En caso de error devuelve un trabajador
	 *         vacio.
	 */
	@ApiOperation(value = "M??todo obtener un trabajador mediante su id.", notes = "", tags = "getWorkerById")
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
	 * Metodo para la creaci??n de un nuevo trabajador.
	 * 
	 * @param Trabajador que se va a crear.
	 * @return Trabajador creado en la BBDD. En caso de error devuelve un trabajador
	 *         vacio.
	 */
	@ApiOperation(value = "M??todo para la creaci??n de un nuevo trabajador con foto.", notes = "", tags = "createWorkerFull")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@RequestMapping(path = "/foto", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Worker> createWorkerFull(@Valid @RequestPart Worker worker, @Valid  MultipartFile multipartFile) {
		if (worker != null  && worker.getId() == -1) {
			try {
				try {
					if (multipartFile !=null) {
					BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
					if (bi == null) {
						return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
					}
					//Subo la foto a drive y obtengo su url para guardarla en la base de datos.
					File file=fileUploadService.uploadToLocal(multipartFile);
					DriveService.getService();
					String direccion = DriveService.uploadFile(file);
					worker.setPicture(direccion);
					
					fileUploadService.flushTmp();
					}else {
						worker.setPicture("http://drive.google.com/uc?export=view&id=1iNbUIohjg-KoZPD-g6Z0fZo9Q7k8GTUF");
					}
				} catch (Exception e) {
					worker.setPicture("http://drive.google.com/uc?export=view&id=1iNbUIohjg-KoZPD-g6Z0fZo9Q7k8GTUF");
				}

				Worker result = service.createWorker(worker);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo para la creaci??n de un nuevo trabajador.
	 * 
	 * @param Trabajador que se va a crear.
	 * @return Trabajador creado en la BBDD. En caso de error devuelve un trabajador
	 *         vacio.
	 */
	@ApiOperation(value = "M??todo para la creaci??n de un nuevo trabajador.", notes = "", tags = "createWorker")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	//@PostMapping("/new/{worker}")
	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public ResponseEntity<Worker> createWorker(@Valid @RequestBody Worker worker) {
		if (worker != null  && worker.getId() == -1) {
			try {
				Worker result = service.createWorker(worker);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo para la actulizaci??n de un trabajador.
	 * 
	 * @param Trabajador que se va a actualizar.
	 * @return Trabajador actualizado en la BBDD. En caso de error devuelve un
	 *         trabajador vacio.
	 */
	@ApiOperation(value = "M??todo para la actulizaci??n de un trabajador.", notes = "", tags = "updateWorker")
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
	 * Metodo obtener un trabajador mediante su nombre.
	 * 
	 * @param Nombre del trabajador a buscar.
	 * @return Trabajador encontrado por nombre. En caso de error devuelve un
	 *         trabajador vacio.
	 */
	@ApiOperation(value = "M??todo obtener un trabajador mediante su nombre.", notes = "", tags = "getWorkerByName")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/name/{name}")
	public ResponseEntity<Worker> getWorkerByName(@PathVariable("name") String name) {
		if (name != null && name.length() > 1) {
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
	 * Metodo obtener un trabajador mediante su apellido.
	 * 
	 * @param Apellido del trabajador a buscar.
	 * @return Trabajador encontrado por apellido. En caso de error devuelve un
	 *         trabajador vacio.
	 */
	@ApiOperation(value = "M??todo obtener un trabajador mediante su apellido.", notes = "", tags = "getWorkerBySurname")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/surname/{surname}")
	public ResponseEntity<Worker> getWorkerBySurname(@PathVariable("surname") String surname) {
		if (surname != null && surname.length() > 1) {
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
	 * Metodo obtener un trabajador mediante su email.
	 * 
	 * @param email del trabajador a buscar.
	 * @return Trabajador encontrado por email. En caso de error devuelve un
	 *         trabajador vacio.
	 */
	@ApiOperation(value = "M??todo obtener un trabajador mediante su email.", notes = "", tags = "getWorkerByEmail")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Worker.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("/email/{email}")
	public ResponseEntity<Worker> getWorkerByEmail(@PathVariable("email") String email) {
		if (email != null && email.length() > 1) {
			try {
				Worker result = service.getWorkerByEmail(email);
				return new ResponseEntity<Worker>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Worker>(new Worker(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo obtener un trabajador por si esta activo/inactivo.
	 * 
	 * @param Activo/No activo atributo del trabajador.
	 * @return Lista de trabajadores activos/inactivos. En caso de error devuelve un
	 *         lista vacia.
	 */
	@ApiOperation(value = "M??todo obtener un trabajador por si esta activo/inactivo.", notes = "", tags = "getWorkerByActive")
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
			return new ResponseEntity<List<Worker>>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo para borra un trabajador de la BBDD.
	 * 
	 * @param id del trabajador a borrar.
	 * @return Status ok si lo borra, Bad_request en caso de no borrarlo.
	 * @throws INTERNAL_SERVER_ERRO Lanzado al no poder realizar la petici??n.
	 */
	@ApiOperation(value = "M??todo para borra un trabajador de la BBDD.", notes = "", tags = "deleteWorkerById")
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

	/**
	 * Metodo para introducir un trabajador en una obra.
	 * 
	 * @params workerId: id del trabajador; workId: id del trabajo.
	 * @return Modelo WorkerWork creado en la BD. En caso de error devuelve un
	 *         WorkerWork vacio.
	 */
	@ApiOperation(value = "M??todo para introducir un trabajador en una obra.", notes = "", tags = "addWorkerToWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping("add/{id}/{workId}")
	public ResponseEntity<WorkerWork> addWorkerToWork(@PathVariable("id") Long workerId,
			@PathVariable("workId") Long workId) {
		Worker worker = service.getWorkerById(workerId);
		Work work = wservice.getWorkById(workId);
		if (worker != null && work != null) {
			
			try {
				WorkerWork result = wwservice
						.createWorkerWork(new WorkerWork(worker, work, true, new HashSet<Dailylog>()));
				return new ResponseEntity<WorkerWork>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception ex) {
				return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<WorkerWork>(new WorkerWork(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo para obtener los workerWorks de una obra segun si est??n activos o no.
	 * 
	 * @params idWork: id de la obra; current: boolean con el valor de activos/inactivos
	 * 
	 * @return Status OK y lista de WorkerWorks si encuentra registros. BAD_REQUEST y lista vac??a si no lo consigue.
	 */
	@ApiOperation(value = "M??todo para obtener workerWorks de un trabajo seg??n si est??n activos", notes = "", tags = "getWorkerWorksFromWorkByActive")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = WorkerWork.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping("workerWork/{idWork}/{current}")
	public ResponseEntity<List<WorkerWork>> getWorkerWorksFromWorkByActive(@PathVariable("idWork") Long idWork, @PathVariable("current") boolean current) {
		List<WorkerWork> result = new ArrayList<WorkerWork>();
		if (idWork > 0) {
			try {
				result = wwservice.findByWorkActive(idWork, current);
				return new ResponseEntity<List<WorkerWork>>(result, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception ex) {
				return new ResponseEntity<List<WorkerWork>>(new ArrayList<WorkerWork>(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<List<WorkerWork>>(new ArrayList<WorkerWork>(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Metodo para eliminar un trabajador de una obra.
	 * 
	 * @params id: ID de la relaci??n a desactivar.
	 * @return Status OK si lo borra. BAD_REQUEST si no lo consigue.
	 */
	@ApiOperation(value = "M??todo para eliminar un trabajador de una obra.", notes = "", tags = "deleteWorkerFromWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = HttpStatus.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@DeleteMapping("workerWork/{id}")
	public HttpStatus deleteWorkerWork(@PathVariable("id") Long id) {
		if (id > 0) {
			try {
				wwservice.deleteWorkerWorkById(id);
				return HttpStatus.OK;
			} catch (Exception ex) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
	
	/**
	 * Metodo para activar un trabajador en una obra.
	 * 
	 * @params workerId: id del trabajador; workId: id del trabajo.
	 * @return Status OK si lo edita. BAD_REQUEST si no lo consigue.
	 */
	@ApiOperation(value = "M??todo para activar un trabajador en una obra.", notes = "", tags = "updateWorkerFromWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = HttpStatus.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PutMapping("workerWork/{id}")
	public HttpStatus updateWorkerWork(@PathVariable("id") Long id) {
		WorkerWork workerwork = wwservice.getWorkerWorkById(id);
		if (workerwork.getId() != null) {
			try {
				workerwork.setCurrent(true);
				wwservice.updateWorkerWork(workerwork);
				return HttpStatus.OK;
			} catch (Exception ex) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
	
	/**
	 * Metodo para desactivar todas las relaciones en obras de un trabajador
	 * 
	 * @params workerId: id del trabajador
	 * @return Status OK si lo edita. BAD_REQUEST si no lo consigue.
	 */
	@ApiOperation(value = "M??todo para desactivar todos los workerWorks de un trabajador", notes = "", tags = "updateWorkerFromWork")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = HttpStatus.class),
			@ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@DeleteMapping("deleteWorkerWorks/{idWorker}")
	public HttpStatus disableWorkerWorksFromWorker(@PathVariable("idWorker") Long idWorker) {
		Worker worker = this.service.getWorkerById(idWorker);
		if (worker != null) {
			try {
				for(WorkerWork ww : worker.getWorkerWork()) {
					ww.setCurrent(false);
					wwservice.updateWorkerWork(ww);
				}
				return HttpStatus.OK;
			} catch (Exception ex) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}

}
