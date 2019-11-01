package com.ripper.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -550953426052476006L;

	public BadRequestException(String message) {
		super(message);
	}

}
