package com.ripper.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.ripper.exception.InvalidDataPassedException;

public class ValidationHandler {
	
	public static void validateWithoutNotNullErrorCode(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				// we have to exclude NotNull validation error because in update call properties
				// could be null
				if (ValidationErrorCode.fromString(fieldError.getCode()) != ValidationErrorCode.NOT_NULL) {
					errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
				}
			}
			if (!errors.isEmpty())
				throw new InvalidDataPassedException(errors);
		}
	}
}
