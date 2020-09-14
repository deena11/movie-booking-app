package com.example.moviesearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviesearch.config.SearchBuilder;
import com.example.moviesearch.restApiConfig.ApiSuccessResponse;


@RestController
@CrossOrigin
@RequestMapping("/search")
public class MainController {
	
	@Autowired
    private SearchBuilder searchBuilder;

    @GetMapping(value = "/{searchText}")
    public ResponseEntity<?> getAll(@PathVariable final String searchText) {
    	
    	ApiSuccessResponse response = new ApiSuccessResponse();
    	response.setError(false);
    	response.setHttpStatus("Success");
    	response.setHttpStatusCode(200);
    	response.setMessage("Sucessfully Fetched Data");
    	response.setBody(searchBuilder.getAll(searchText));
    	
    	
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

	
	
//	@GetMapping("/hello")
//	public ResponseEntity<?> searchMovies(){
//		return ResponseEntity.status(HttpStatus.OK).body(playDataRepository.findAll());
//	}

}
