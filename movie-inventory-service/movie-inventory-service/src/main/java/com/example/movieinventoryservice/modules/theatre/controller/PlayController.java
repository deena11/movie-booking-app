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

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/play")
public class PlayController {
		
	private Logger logger = LoggerFactory.getLogger(PlayController.class);
		
		@Autowired
		private PlayService playService;

		@GetMapping("/{playId}")
		public ResponseEntity<?> getPlay(@PathVariable("playId") int playId) throws RecordNotFoundException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(playService.getPlayById(playId));

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

		@GetMapping("/all")
		public ResponseEntity<?> getAllPlay() throws EmptyListException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(playService.getAllPlays());

			return ResponseEntity.ok(response);
		}

		@PostMapping("/")
		public ResponseEntity<?> addPlay(@RequestBody Play play) throws RecordNotAddedException {

			logger.info(play.toString());
			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully Added Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(playService.addPlay(play));

			return ResponseEntity.ok(response);
		}

		@PutMapping("/")
		public ResponseEntity<?> updatePlay(@RequestBody Play play) throws RecordNotUpdatedException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(playService.updatePlay(play));

			return ResponseEntity.ok(response);
		}

		@DeleteMapping("/{playId}")
		public ResponseEntity<?> deletePlay(@PathVariable("playId") int playId) throws RecordNotDeletedException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(playService.deletePlay(playId));

			return ResponseEntity.ok(response);
		}




}
