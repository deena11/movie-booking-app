package com.example.bookingservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.model.Booking;

public interface BookingService {

	Booking addBooking(Booking booking,HttpServletRequest request) throws BookingServiceDaoException;

	List<Booking> getAllBooking() throws BookingServiceDaoException;

	Booking getBookingById(int bookingId,HttpServletRequest request) throws BookingServiceDaoException;

	Booking updateBooking(Booking booking,HttpServletRequest request) throws BookingServiceDaoException;

	String deleteBooking(int bookingId,HttpServletRequest request) throws BookingServiceDaoException;

}
