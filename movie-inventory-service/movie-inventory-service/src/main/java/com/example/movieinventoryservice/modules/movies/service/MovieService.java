package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface MovieService {
	
	public Movie addMovie(Movie movie) throws RecordNotAddedException;
	
	public String deleteMovie(int movieId) throws RecordNotDeletedException;
	
	public String updateMovie(Movie movie) throws RecordNotUpdatedException;
	
	public Movie getMovieById(int movieId) throws RecordNotFoundException;
	
	public List<Movie> getAllMovies() throws EmptyListException;

}
