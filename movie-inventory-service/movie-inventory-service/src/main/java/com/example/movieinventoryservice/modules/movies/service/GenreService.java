package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface GenreService {
	
	public Genre addGenre(Genre genre) throws RecordNotAddedException;

	public String deleteGenre(int genreId) throws RecordNotDeletedException;

	public String updateGenre(Genre genre) throws RecordNotUpdatedException;

	public Genre getGenreById(int genreId) throws RecordNotFoundException;

	public List<Genre> getAllGenres() throws EmptyListException;

}
