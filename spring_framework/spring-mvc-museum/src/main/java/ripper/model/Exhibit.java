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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "exhibits_exhibitions", joinColumns = @JoinColumn(name = "exhibit_id"), inverseJoinColumns = @JoinColumn(name = "exhibition_id"))
	private Set<Exhibition> exhibitions;

	@OneToMany(mappedBy = "exhibit", cascade = CascadeType.ALL)
	private Set<Loan> loans = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
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
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
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
		this.exhibitionRoom = exhibitionRoom;
	}

	public Person getArtist() {
		return artist;
	}

	public void setArtist(Person artist) {
		this.artist = artist;
	}

	public Set<Loan> getLoans() {
		return new HashSet<>(loans);
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}

	public void addLoan(Loan loan) {
		loans.add(loan);
	}

	public void removeLoan(Loan loan) {
		loan.remove();
	}

	@Override
	public String toString() {
		return "Exhibit [id=" + id + ", name=" + name + "]";
	}

}
