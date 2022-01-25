package com.ajaguilar.apiRestful.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajaguilar.apiRestful.exceptions.RecordNotFoundException;
import com.ajaguilar.apiRestful.model.Notes;
import com.ajaguilar.apiRestful.repository.NotesRepository;

@Service
public class NotesService {
	@Autowired
	NotesRepository repository;
	
	public List<Notes> getAllNotes(){
		List<Notes> result=repository.findAll();
		return result;
	}
	public Notes getNotesById(Long id) throws RecordNotFoundException{
		Optional<Notes> result=repository.findById(id);
		if (result.isPresent()) {
			return result.get();
			
		}else {
			throw new RecordNotFoundException("La nota no existe", id);
		}
	}
	public Notes createOrUpdateNotes(Notes note) throws RecordNotFoundException{
		if (note.getId()!=null && note.getId()>0) {
			Optional<Notes> n=repository.findById(note.getId());
			if (n.isPresent()) {//Update
				Notes newNote = n.get();
				newNote.setId(note.getId());
			newNote.setTitle(note.getTitle());
				newNote.setDescription(note.getDescription());
				newNote = repository.save(newNote);
				return newNote;
			}else {//INSERT
				note.setId(null);
				note=repository.save(note);
				return note;
			}
		}else {
			note=repository.save(note);
			return note;
		}
	}
	
	public void deleteNotesById(Long id) throws RecordNotFoundException{
		Optional<Notes> note = repository.findById(id);
		if (note.isPresent()) {
			repository.deleteById(id);
		}else {
			throw new RecordNotFoundException("La nota no existe", id);
		}
	}
}
