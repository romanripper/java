package com.ripper.validation;



public enum ValidationErrorCode {
	NOT_NULL("NotNull"), SIZE("Size"), MIN("Min"), MAX("Max");

	private final String errorCode;

	private ValidationErrorCode(final String text) {
		this.errorCode = text;
	}

	@Override
	public String toString() {
		return errorCode;
	}

	public static ValidationErrorCode fromString(String text) {
		for (ValidationErrorCode value : ValidationErrorCode.values()) {
			if (value.errorCode.equalsIgnoreCase(text)) {
				return value;
			}
		}
		throw new IllegalArgumentException("There is no such error code registered in the system");
	}
}
