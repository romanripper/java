package com.ripper.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.DataExistsException;
import com.ripper.exception.InvalidDataPassedException;
import com.ripper.exception.NotFoundException;
import com.ripper.response.ControllerErrorResponse;

@ControllerAdvice
@PropertySource({ "classpath:errorMessages.properties" })
public class MyExceptionHandler {

	@Autowired
	private Environment env;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ControllerErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.collect(Collectors.toList());

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(), errors,
				System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ControllerErrorResponse> handleInvalidFormatException(InvalidFormatException e) {

		String errorMessage = e.getPath().get(0).getFieldName() + ": " + env.getProperty(e.getTargetType().getName());

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				errorMessage, System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(InvalidDataPassedException.class)
	public ResponseEntity<ControllerErrorResponse> handleInvalidDataPassedException(InvalidDataPassedException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getErrorMessages(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ControllerErrorResponse> handleNotFoundException(NotFoundException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.NOT_FOUND.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ControllerErrorResponse> handleBadRequestException(BadRequestException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(DataExistsException.class)
	public ResponseEntity<ControllerErrorResponse> handleDataExistsException(DataExistsException e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	// global exception handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ControllerErrorResponse> handleException(Exception e) {

		ControllerErrorResponse errorResponse = new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

}
