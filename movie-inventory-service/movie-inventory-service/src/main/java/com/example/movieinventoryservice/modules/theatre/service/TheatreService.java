package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface TheatreService {
	
	public Theatre addTheatre(Theatre theatre) throws RecordNotAddedException;
	
	public String deleteTheatre(int theatreId) throws RecordNotDeletedException;
	
	public String updateTheatre(Theatre theatre) throws RecordNotUpdatedException;
	
	public Theatre getTheatreById(int theatreId) throws RecordNotFoundException;
	
	public List<Theatre> getAllTheatres() throws EmptyListException;


}
