package com.example.bookingservice.response;

public class APISuccessResponse {
	private String httpStatus;
	private int statusCode;
	private String message;
	private Object body;
	public APISuccessResponse() {
		super();
	}
	public APISuccessResponse(String httpStatus, int statusCode, String message, Object body) {
		super();
		this.httpStatus = httpStatus;
		this.statusCode = statusCode;
		this.message = message;
		this.body = body;
	}
	public String getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "APISuccessResponse [httpStatus=" + httpStatus + ", statusCode=" + statusCode + ", message=" + message
				+ ", body=" + body + "]";
	}

	
	
}
