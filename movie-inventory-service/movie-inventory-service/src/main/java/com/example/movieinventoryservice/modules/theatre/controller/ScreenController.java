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

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/screen")
public class ScreenController {
	
	private Logger logger = LoggerFactory.getLogger(ScreenController.class);
		
		@Autowired
		private ScreenService screenService;

		@GetMapping("/{screenId}")
		public ResponseEntity<?> getScreen(@PathVariable("screenId") int screenId) throws RecordNotFoundException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(screenService.getScreenById(screenId));

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

		@GetMapping("/")
		public ResponseEntity<?> getAllScreen() throws EmptyListException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(screenService.getAllScreens());

			return ResponseEntity.ok(response);
		}

		@PostMapping("/")
		public ResponseEntity<?> addScreen(@RequestBody Screen screen) throws RecordNotAddedException {

			logger.info(screen.toString());
			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully Added Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(screenService.addScreen(screen));

			return ResponseEntity.ok(response);
		}

		@PutMapping("/")
		public ResponseEntity<?> updateScreen(@RequestBody Screen screen) throws RecordNotUpdatedException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(screenService.updateScreen(screen));

			return ResponseEntity.ok(response);
		}

		@DeleteMapping("/{screenId}")
		public ResponseEntity<?> deleteScreen(@PathVariable("screenId") int screenId) throws RecordNotDeletedException {

			ApiSuccessResponse response = new ApiSuccessResponse();
			response.setError(false);
			response.setMessage("Successfully fetched Data");
			response.setSuccess(true);
			response.setHttpStatus(HttpStatus.OK.toString());
			response.setBody(screenService.deleteScreen(screenId));

			return ResponseEntity.ok(response);
		}


	

}
