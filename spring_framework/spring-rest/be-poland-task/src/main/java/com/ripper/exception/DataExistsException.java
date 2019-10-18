package com.ripper.exception;

public class DataExistsException extends RuntimeException{

	public DataExistsException() {
		super();
	}

	public DataExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataExistsException(String message) {
		super(message);
	}

	public DataExistsException(Throwable cause) {
		super(cause);
	}
	
}
