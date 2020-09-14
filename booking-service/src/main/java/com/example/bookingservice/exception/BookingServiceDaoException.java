package com.example.bookingservice.exception;

public class BookingServiceDaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookingServiceDaoException() {
		super();
	}

	public BookingServiceDaoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BookingServiceDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookingServiceDaoException(String message) {
		super(message);
	}

	public BookingServiceDaoException(Throwable cause) {
		super(cause);
	}

	
	
}
