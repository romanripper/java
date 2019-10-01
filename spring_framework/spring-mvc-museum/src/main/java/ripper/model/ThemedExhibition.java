package ripper.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(value = "THEMED")
public class ThemedExhibition extends Exhibition {
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "theme")
	private String theme;
	
	public ThemedExhibition() {
		
	}

	public ThemedExhibition(String title, LocalDate openDate, LocalDate closeDate, double ticketPrice,
			String theme, Person curator, Set<String> keywords) {
		super(title, openDate, closeDate, ticketPrice, curator, keywords);
		setTheme(theme);
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
}
