package ripper.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ConsistentDatesValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentDates {
	
	String message() default "close date must be after open date";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};
	
}
