package com.example.userservice.exception;

public class RecordNotDeletedException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public RecordNotDeletedException() {
		super();
	}

	public RecordNotDeletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RecordNotDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordNotDeletedException(String message) {
		super(message);
	}

	public RecordNotDeletedException(Throwable cause) {
		super(cause);
	}
	
	

}
