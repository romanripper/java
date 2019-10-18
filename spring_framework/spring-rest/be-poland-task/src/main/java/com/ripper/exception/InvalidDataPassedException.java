package com.ripper.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidDataPassedException extends RuntimeException {

	private List<String> errorMessages;


	public InvalidDataPassedException(String error) {
		this.errorMessages = new ArrayList<>();
		errorMessages.add(error);
	}
	
	public InvalidDataPassedException(List<String> errors) {
		this.errorMessages = errors;
	}

	public InvalidDataPassedException(String message, List<String> errors) {
		super(message);
		this.errorMessages = errors;
	}

	public List<String> getErrorMessages() {
		return new ArrayList<>(errorMessages);
	}

	public void setErrorMessages(List<String> errors) {
		this.errorMessages = errors;
	}

}
