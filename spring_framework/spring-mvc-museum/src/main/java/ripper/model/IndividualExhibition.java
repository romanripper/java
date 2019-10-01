package ripper.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "INDIVIDUAL")
public class IndividualExhibition extends Exhibition {

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "artist_id")
	private Person artist;

	public IndividualExhibition() {

	}

	public IndividualExhibition(String title, LocalDate openDate, LocalDate closeDate, double ticketPrice,
			Person artist, Person curator, Set<String> keywords) {
		super(title, openDate, closeDate, ticketPrice, curator, keywords);
		setArtist(artist);
	}

	public Person getArtist() {
		return artist;
	}

	public void setArtist(Person artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "IndividualExhibition [artist=" + artist + ", toString()=" + super.toString() + "]";
	}
	
	

}
