package com.example.movieinventoryservice.modules.movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/movie")
public class MovieController {

	private Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	@Autowired
	private MovieService movieService;

	@GetMapping("/{movieId}")
	public ResponseEntity<?> getMovie(@PathVariable("movieId") int movieId) throws RecordNotFoundException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(movieService.getMovieById(movieId));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllMovie() throws EmptyListException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(movieService.getAllMovies());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws RecordNotAddedException {

		logger.info(movie.toString());
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully Added Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(movieService.addMovie(movie));

		return ResponseEntity.ok(response);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws RecordNotUpdatedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(movieService.updateMovie(movie));

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{movieId}")
	public ResponseEntity<?> deleteMovie(@PathVariable("movieId") int movieId) throws RecordNotDeletedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(movieService.deleteMovie(movieId));

		return ResponseEntity.ok(response);
	}

}
