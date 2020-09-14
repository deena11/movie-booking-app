package com.example.bookingservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.service.BookingService;


@RestController
@CrossOrigin
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> addBooking(@RequestBody Booking booking,HttpServletRequest request) throws BookingServiceDaoException
	{
		Booking bookingDetails=bookingService.addBooking(booking,request);
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Booking Saved Successfull");
		response.setBody(bookingDetails);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@GetMapping("/")
//	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> getAllBooking() throws BookingServiceDaoException  {
		List<Booking> bookingList = new ArrayList<Booking>();
		bookingList = bookingService.getAllBooking();
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Get all Booking Successfull");
		response.setBody(bookingList);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<?> getBookingById(@PathVariable int bookingId,HttpServletRequest request) throws BookingServiceDaoException   {
		Booking booking = bookingService.getBookingById(bookingId,request);
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Get Booking by Id Successfull");
		response.setBody(booking);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateBooking(@RequestBody Booking booking,HttpServletRequest request) throws BookingServiceDaoException
	{
		Booking bookingDetails=bookingService.updateBooking(booking,request);
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Booking Updated Successfull");
		response.setBody(bookingDetails);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED)).body(response);	
	}

	@DeleteMapping("/{bookingId}")
	public ResponseEntity<?> deleteUser(@PathVariable int bookingId,HttpServletRequest request) throws BookingServiceDaoException {
		
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Booking Deleted Successfull");
		response.setBody(bookingService.deleteBooking(bookingId,request));
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}
	

}
