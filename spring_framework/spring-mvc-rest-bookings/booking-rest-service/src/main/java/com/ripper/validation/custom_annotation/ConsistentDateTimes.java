package com.ripper.validation.custom_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ripper.validation.ValidationConstants;

@Constraint(validatedBy = ConsistentDateTimesValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentDateTimes {
	
	String message() default ValidationConstants.CONSISTENT_DATE_TIMES_MESSAGE;
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};
	
}
