package com.example.movieinventoryservice.exception.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.restApiConfig.ApiErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse response, HttpStatus httpStatus) {

		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(httpStatus)).body(response);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		BindingResult binding = ex.getBindingResult();
		FieldError error = binding.getFieldError();
		String message = error.getDefaultMessage();
		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setHttpStatusCode(400);
		response.setError(true);

		
		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatchExceptions(
			final MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();
		
		String message = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		 
		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setHttpStatusCode(400);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleIllegalStateException(
			final MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		System.out.println("type mismatch");
		ApiErrorResponse response = new ApiErrorResponse();
		
		String message = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		 
		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setHttpStatusCode(400);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
	  MissingServletRequestParameterException ex, HttpHeaders headers, 
	  HttpStatus status, WebRequest request) {
	    
	    ApiErrorResponse response = new ApiErrorResponse();
	    String message = ex.getParameterName() + " should be of type " + ex.getParameterType();
		 
		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setHttpStatusCode(400);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		response.setCause(ex.getLocalizedMessage());
		response.setMessage(ex.getMessage());
		logger.error("exception occured - ");
		ex.printStackTrace();
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setHttpStatusCode(500);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}


	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleRecordNotFoundExceptions(RecordNotFoundException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record not found.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		response.setHttpStatusCode(404);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(EmptyListException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public final ResponseEntity<Object> handleEmptyListFoundExceptions(EmptyListException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record not found.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.NO_CONTENT);
		response.setHttpStatusCode(404);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.NO_CONTENT);

	}

	@ExceptionHandler(RecordNotAddedException.class)
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	public final ResponseEntity<Object> handleRecordNotCreatedExceptions(RecordNotAddedException ex,
			WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record not created");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		response.setHttpStatusCode(422);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@ExceptionHandler(RecordNotDeletedException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public final ResponseEntity<Object> handleRecordNotDeletedExceptions(RecordNotDeletedException ex,
			WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record was not deleted.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.CONFLICT);
		response.setHttpStatusCode(409);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(RecordNotUpdatedException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public final ResponseEntity<Object> handleRecordNotUpdatedExceptions(RecordNotUpdatedException ex,
			WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record did not got update.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.CONFLICT);
		response.setHttpStatusCode(409);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.CONFLICT);

	}

		
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("IOException - Something went wrong");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setHttpStatusCode(500);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("IllegalArgumentException - Something went wrong");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setHttpStatusCode(500);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		logger.error("exception occured - Nullpointer- ");
		ex.printStackTrace();
		response.setMessage("Something went wrong. Object is empty or null.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setHttpStatusCode(500);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	

}
