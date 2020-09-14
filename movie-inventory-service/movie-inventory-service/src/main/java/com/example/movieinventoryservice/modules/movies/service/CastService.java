package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface CastService {
public Cast addCast(Cast cast) throws RecordNotAddedException;
	
	public String deleteCast(int castId) throws RecordNotDeletedException;
	
	public String updateCast(Cast cast) throws RecordNotUpdatedException;
	
	public Cast getCastById(int castId) throws RecordNotFoundException;
	
	public List<Cast> getAllCasts() throws EmptyListException;


}
