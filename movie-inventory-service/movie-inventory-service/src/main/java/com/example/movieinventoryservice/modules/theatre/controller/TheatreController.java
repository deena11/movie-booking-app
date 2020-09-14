package com.example.movieinventoryservice.modules.theatre.controller;

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

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/theatre")
public class TheatreController {
	
private Logger logger = LoggerFactory.getLogger(TheatreController.class);
	
	@Autowired
	private TheatreService theatreService;

	@GetMapping("/{theatreId}")
	public ResponseEntity<ApiSuccessResponse> getTheatre(@PathVariable("theatreId") int theatreId) throws RecordNotFoundException {

		logger.info("Theatre Service is called");
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(theatreService.getTheatreById(theatreId));
		logger.info(response.toString());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllTheatre() throws EmptyListException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(theatreService.getAllTheatres());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/")
	public ResponseEntity<?> addTheatre(@RequestBody Theatre theatre) throws RecordNotAddedException {

		logger.info(theatre.toString());
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully Added Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(theatreService.addTheatre(theatre));
		
		return ResponseEntity.ok(response);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateTheatre(@RequestBody Theatre theatre) throws RecordNotUpdatedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(theatreService.updateTheatre(theatre));

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{theatreId}")
	public ResponseEntity<?> deleteTheatre(@PathVariable("theatreId") int theatreId) throws RecordNotDeletedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(theatreService.deleteTheatre(theatreId));

		return ResponseEntity.ok(response);
	}


}
