package com.example.movieinventoryservice.exception;

public class EmptyListException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyListException() {
		super();
	}

	public EmptyListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyListException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyListException(String message) {
		super(message);
	}

	public EmptyListException(Throwable cause) {
		super(cause);
	}
	
	

}
