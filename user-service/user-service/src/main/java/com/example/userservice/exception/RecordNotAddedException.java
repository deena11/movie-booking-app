package com.example.userservice.exception;

public class RecordNotAddedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordNotAddedException() {
		super();
	}

	public RecordNotAddedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RecordNotAddedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordNotAddedException(String message) {
		super(message);
	}

	public RecordNotAddedException(Throwable cause) {
		super(cause);
	}
	

}
