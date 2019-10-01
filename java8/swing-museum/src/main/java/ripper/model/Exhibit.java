package ripper.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "exhibit")
public class Exhibit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "exhibition_id")
	private Exhibition exhibition;

	@OneToMany(mappedBy = "exhibit", cascade = CascadeType.ALL)
	private Set<Loan> loans = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "artist_id")
	private Person artist;

	@Column(name = "is_currently_loaned", nullable = false)
	private boolean isCurrentlyLoaned;

	@Column(name = "is_damaged", nullable = false)
	private boolean isDamaged;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "exhibition_room_id")
	private ExhibitionRoom exhibitionRoom;

	public Exhibit() {

	}

	public Exhibit(String name, String description) {
		setName(name);
		setDescription(description);
	}

	public Exhibit(String name, String description, Person artist) {
		setName(name);
		setDescription(description);
		setArtist(artist);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new RuntimeException("Name can not be null");
		}
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) {
			throw new RuntimeException("Description can not be null");
		}
		this.description = description;
	}

	public boolean isCurrentlyLoaned() {
		return isCurrentlyLoaned;
	}

	public void setCurrentlyLoaned(boolean isCurrentlyLoaned) {
		this.isCurrentlyLoaned = isCurrentlyLoaned;
	}

	public boolean isDamaged() {
		return isDamaged;
	}

	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

	public ExhibitionRoom getExhibitionRoom() {
		return exhibitionRoom;
	}

	public void setExhibitionRoom(ExhibitionRoom exhibitionRoom) {
		if (this.exhibitionRoom != exhibitionRoom) {
			if (exhibitionRoom != null) {
				if (exhibition == null) {
					throw new RuntimeException("This exhibit is not a part of any exhibition");
				}
				if (!exhibition.getExhibitionRooms().contains(exhibitionRoom)) {
					throw new RuntimeException("Exhibition is not in this exhibition room");
				}
				if (this.exhibitionRoom != null) {
					this.exhibitionRoom.removeExhibit(this);
				}
				this.exhibitionRoom = exhibitionRoom;
				this.exhibitionRoom.addExhibit(this);
			} else {
				if (this.exhibitionRoom != null) {
					this.exhibitionRoom.removeExhibit(this);

				}
				this.exhibitionRoom = exhibitionRoom;
				System.out.println("Exhibit: setExhibitionRoom()");
			}
		}
	}

	public Person getArtist() {
		return artist;
	}

	public void setArtist(Person author) {
		if (this.artist != author) {
			if (author != null) {
				if (this.artist != null) {
					this.artist.removeExhibit(this);
				}
				this.artist = author;
				this.artist.addExhibit(this);
			} else {
				if (this.artist != null) {
					this.artist.removeExhibit(this);
				}
				this.artist = author;
			}

		}
	}

	public Exhibition getExhibition() {
		return exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		if (this.exhibition != exhibition) {
			if (exhibition != null) {
				if (this.exhibition != null) {
					setExhibitionRoom(null);
					this.exhibition.removeExhibit(this);
				}
				this.exhibition = exhibition;
				this.exhibition.addExhibit(this);
			} else {
				setExhibitionRoom(null);
				if (this.exhibition != null) {
					this.exhibition.removeExhibit(this);
				}
				this.exhibition = exhibition;
				System.out.println("Exhibit: setExhibition");
			}
		}

	}

	public Set<Loan> getLoans() {
		return new HashSet<>(loans);
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}

	public void addLoan(Loan loan) {
		if (!(loans.contains(loan))) {
			if (loan == null) {
				throw new RuntimeException("Loan can not be null");
			}
			if (loan.getExhibit() != this) {
				throw new RuntimeException("Loan of different exhibit");
			}
			loans.add(loan);
		}
	}

	public void removeLoan(Loan loan) {
		if (loans.contains(loan)) {
			loans.remove(loan);
			loan.remove();
		}
	}

	@Override
	public String toString() {
		return "Exhibit{" + "name=" + name + '}';
	}

}
