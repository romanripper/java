package ripper.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "INDIVIDUAL")
public class IndividualExhibition extends Exhibition {

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
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

	private void setArtist(Person artist) {
		if (artist == null) {
			throw new RuntimeException("Artist can not be null");
		}
		if (artist.getRoles().contains(Role.ARTIST)) {
			this.artist = artist;
			artist.addIndividualExhibition(this);
		} else {
			throw new RuntimeException("This person is not artist, he can not have individual exhibitions");
		}
	}

	public void remove() {
		if (artist != null) {
			artist.removeIndividualExhibition(this);
			artist = null;
		}
	}

}
