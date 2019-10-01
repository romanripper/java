package ripper.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "THEMED")
public class ThemedExhibition extends Exhibition {
	
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
		if(theme == null) {
			throw new RuntimeException("Theme ca not be null");
		}
		this.theme = theme;
	}
	
}
