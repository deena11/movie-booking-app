package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.repository.MovieRepository;
import com.example.movieinventoryservice.modules.movies.service.MovieService;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	private String message = "";

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CastRepository castRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public Movie addMovie(Movie movie) throws RecordNotAddedException {

		try {

			
			movie.getGenre().forEach(val->{
				Optional<Integer>genreId = genreRepository.getIdByName(val.getName());
				if(genreId.isPresent()) {
					val.setId(genreId.get());
				}
			});
			movie.getGenre().forEach(v -> genreRepository.save(v));

			List<Cast> cast = movie.getCast();
			cast.forEach(v -> castRepository.save(v));

			return movieRepository.save(movie);
		} catch (DataAccessException ex) {
			System.out.println(ex.getCause());
			throw new RecordNotAddedException("Failed To Add Movie", ex.getCause());
		}

	}

	@Override
	public String deleteMovie(int movieId) throws RecordNotDeletedException {
		try {
			movieRepository.deleteById(movieId);
			message = "Successfully deleted movie of id - " + movieId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}

	}

	@Override
	public String updateMovie(Movie movie) throws RecordNotUpdatedException {
		try {

			movie.getGenre().forEach(val->{
				Optional<Integer>genreId = genreRepository.getIdByName(val.getName());
				if(genreId.isPresent()) {
					val.setId(genreId.get());
				}
			});
			movie.getGenre().forEach(v -> genreRepository.save(v));

			List<Cast> cast = movie.getCast();
			cast.forEach(v -> castRepository.save(v));

			movieRepository.save(movie);
			message = "Successfully updated movie of name - " + movie.getName();
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}

	}

	@Override
	public Movie getMovieById(int movieId) throws RecordNotFoundException {

		try {
			Optional<Movie> movie = movieRepository.findById(movieId);
			if (movie.isPresent()) {
				return movie.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Movie> getAllMovies() throws EmptyListException {
		try {
			List<Movie> movies = movieRepository.findAll();
			if (movies.size()>0) {
				return movies;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

}
