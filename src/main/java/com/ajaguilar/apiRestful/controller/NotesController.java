package com.ajaguilar.apiRestful.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Notes;
import com.ajaguilar.apiRestful.services.NotesService;

@RestController //Indica que esto es una clase controller
@RequestMapping("/notes") //Cuando se introduzca esta URL se ejecutara este controller
public class NotesController {

	@Autowired  //Instancia service al ejecutar el controller
	NotesService service;
	
	@GetMapping //Lo que ejecutara al llamar a este controller. En este caso, un GET
	public ResponseEntity<List<Notes>> getAllNotes(){
		List<Notes> all=service.getAllNotes();
		return new ResponseEntity<List<Notes>>(all, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}") //Igual que el de arriba pero ejecuta este si has introducido una id en la url
	public ResponseEntity<Notes> getNotesById(@PathVariable("id") Long id){ //Coge la id pasada por la url y la inyecta en el parametro
		Notes note=service.getNotesById(id);
		return new ResponseEntity<Notes>(note, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping //Ejecuta un post
	public ResponseEntity<Notes> createOrUpdateNotes(@Valid @RequestBody Notes n){ //Coge lo que le has pasado en el body e intenta instaciar una note con eso
		Notes note=service.createOrUpdateNotes(n);
		return new ResponseEntity<Notes>(note, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}") //Ejecuta un delete
	public HttpStatus deleteNoteById(Long id) throws RecordNotFoundException{
		service.deleteNotesById(id);
		return HttpStatus.OK;
	}
}
