package com.ripper.validation.custom_annotation;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ripper.request_wrapper.BookingRequestWrapper;

public class ConsistentDateTimesValidator implements ConstraintValidator<ConsistentDateTimes, BookingRequestWrapper> {

	@Override
	public boolean isValid(BookingRequestWrapper bookingRequestWrapper, ConstraintValidatorContext context) {
		if (bookingRequestWrapper == null) {
			return true;
		}

		// @NotNull and @Future annotations will show errors for those expressions that
		// are described below. Also when time frame is open we don't need to check if dateFrom
		// and dateTo are consistent
		if (bookingRequestWrapper.getDateFrom() == null || bookingRequestWrapper.getDateTo() == null
				|| bookingRequestWrapper.getDateFrom().isBefore(LocalDateTime.now())
				|| bookingRequestWrapper.getDateTo().isBefore(LocalDateTime.now())) {
			return true;
		}

		return bookingRequestWrapper.getDateFrom().isBefore(bookingRequestWrapper.getDateTo());
	}

}
