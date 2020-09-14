package com.example.moviesearch.restApiConfig;

public class ApiSuccessResponse {

	private String message;

	private String httpStatus;

	private int httpStatusCode;

	private Object body;

	private boolean success;

	private boolean error;

	public ApiSuccessResponse() {
		super();
	}

	public ApiSuccessResponse(String message, String httpStatus, int httpStatusCode, Object body, boolean success,
			boolean error) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.httpStatusCode = httpStatusCode;
		this.body = body;
		this.success = success;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
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

	@Override
	public String toString() {
		return "ApiSuccessResponse [message=" + message + ", httpStatus=" + httpStatus + ", httpStatusCode="
				+ httpStatusCode + ", body=" + body + ", success=" + success + ", error=" + error + "]";
	}
	
	
	
	
}
