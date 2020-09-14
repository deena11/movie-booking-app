package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.service.GenreService;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {
	
	@Autowired
	private GenreRepository genreRepository;
	
	private String message = "";

	

	@Override
	public String deleteGenre(int genreId) throws RecordNotDeletedException {
		try {
			genreRepository.deleteById(genreId);
			message = "Successfully deleted Genre of id - " + genreId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}
	}

	@Override
	public String updateGenre(Genre genre) throws RecordNotUpdatedException {
		try {

			genreRepository.save(genre);
			message = "Successfully updated genre of name - " + genre.getName();
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
	}

	@Override
	public Genre getGenreById(int genreId) throws RecordNotFoundException {
		try {
			Optional<Genre> genre = genreRepository.findById(genreId);
			if (genre.isPresent()) {
				return genre.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Genre> getAllGenres() throws EmptyListException {
		try {
			List<Genre> genres = genreRepository.findAll();
			if (genres.size()>0) {
				return genres;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public Genre addGenre(Genre genre) throws RecordNotAddedException {
		try {
			return genreRepository.save(genre);
		} catch (DataAccessException ex) {
			throw new RecordNotAddedException("Failed To Add Genre", ex.getCause());
		}
	}

}


