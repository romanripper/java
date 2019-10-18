package com.ripper.utils;

import javax.validation.constraints.NotEmpty;

public enum ValidationErrorCode {
	NOT_NULL("NotNull"), SIZE("Size"), MIN("Min"), MAX("Max"),NOT_EMPTY("NotEmpty");

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
