package com.example.moviesearch.restApiConfig;

public class ApiErrorResponse {
	
	private String message;

	private Object httpStatus;

	private int httpStatusCode;

	private boolean success;

	private boolean error;

	private Object cause;
	
	private Object exceptionMessage;

	public ApiErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiErrorResponse(String message, Object httpStatus, int httpStatusCode, boolean success, boolean error,
			Object cause, Object exceptionMessage) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.httpStatusCode = httpStatusCode;
		this.success = success;
		this.error = error;
		this.cause = cause;
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "ApErrorResponse [message=" + message + ", httpStatus=" + httpStatus + ", httpStatusCode="
				+ httpStatusCode + ", success=" + success + ", error=" + error + ", cause=" + cause
				+ ", exceptionMessage=" + exceptionMessage + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Object httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getCause() {
		return cause;
	}

	public void setCause(Object cause) {
		this.cause = cause;
	}

	public Object getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(Object exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	

}
