package ripper.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ripper.model.Exhibition;

public class ConsistentDatesValidator implements ConstraintValidator<ConsistentDates, Exhibition> {

	@Override
	public boolean isValid(Exhibition exhibition, ConstraintValidatorContext context) {
		if (exhibition == null) {
			return true;
		}

		// @NotNull and @Future annotations will show errors for those expressions that
		// are described below
		if (exhibition.getOpenDate() == null || exhibition.getCloseDate() == null ||
				exhibition.getOpenDate().isBefore(LocalDate.now()) ||
				exhibition.getCloseDate().isBefore(LocalDate.now())) {
			return true;
		}
		return exhibition.getOpenDate().isBefore(exhibition.getCloseDate());
	}

}
