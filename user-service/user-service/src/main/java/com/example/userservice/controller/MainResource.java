package com.example.userservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.entity.User;
import com.example.userservice.exception.AccessTokenRevokeException;
import com.example.userservice.exception.EmptyListException;
import com.example.userservice.exception.RecordNotAddedException;
import com.example.userservice.exception.RecordNotDeletedException;
import com.example.userservice.exception.RecordNotFoundException;
import com.example.userservice.exception.RecordNotUpdatedException;
import com.example.userservice.restApiConfig.ApiSuccessResponse;
import com.example.userservice.service.UserServiceImpl;

@RestController
@RequestMapping("user")
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainResource {

	@Autowired
	private UserServiceImpl userServiceImpl;

	private Logger logger = LoggerFactory.getLogger(MainResource.class);

	@DeleteMapping("/logout")
	public ResponseEntity<?> revokeToken(HttpServletRequest request) throws AccessTokenRevokeException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Loggig out process");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(userServiceImpl.logout(request));

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> getUser(@PathVariable("userId") int userId) throws RecordNotFoundException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(userServiceImpl.fetchById(userId));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_admin')")
	public ResponseEntity<?> getAllUser() throws EmptyListException, RecordNotFoundException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(userServiceImpl.fetchAll());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user) throws RecordNotAddedException {

		logger.info(user.toString());
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully Added Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(userServiceImpl.createUser(user));

		return ResponseEntity.ok(response);
	}

	@PutMapping("/")
	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws RecordNotUpdatedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully Updated Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(userServiceImpl.updateUser(user));

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_admin')")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) throws RecordNotDeletedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully Deleted Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(userServiceImpl.deleteUser(userId));

		return ResponseEntity.ok(response);
	}

}
