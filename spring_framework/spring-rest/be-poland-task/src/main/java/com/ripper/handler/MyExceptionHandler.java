package com.ripper.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.DataExistsException;
import com.ripper.exception.InvalidDataPassedException;
import com.ripper.exception.NotFoundException;
import com.ripper.response.ControllerErrorResponse;
import com.ripper.utils.MyUtils;

@ControllerAdvice
@PropertySource({ "classpath:errorMessages.properties" })
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private Environment env;

	
	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleInvalidFormatException(InvalidFormatException e) {

		String errorMessage = e.getPath().get(0).getFieldName() + ": " + env.getProperty(e.getTargetType().getName());

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				errorMessage, System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleInvalidDataPassedException(InvalidDataPassedException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getErrorMessages(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleNotFoundException(NotFoundException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.NOT_FOUND.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleBadRequestException(BadRequestException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleDataExistsException(DataExistsException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ResponseEntity<Object> response = new ResponseEntity<>(MyUtils.prepareBody(e, status.value()), status);
		
		return response;
	}

	// global exception handler
	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleException(Exception e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

}
