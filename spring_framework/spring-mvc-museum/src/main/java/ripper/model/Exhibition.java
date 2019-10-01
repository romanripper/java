package ripper.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ripper.validation.ConsistentDates;


@Entity
@Table(name = "exhibition")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)

@ConsistentDates
public abstract class Exhibition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "title", nullable = false)
	private String title;


	@NotNull(message = "is required")
	@Future(message = "date must be in future")
	@Column(name = "open_date", nullable = false)
	private LocalDate openDate;


	@NotNull(message = "is required")
	@Future(message = "date must be in future")
	@Column(name = "close_date", nullable = false)
	private LocalDate closeDate;


	@NotNull(message = "is required")
	@Min(value = 0, message = "must be greater or equal to zero")
	@Column(name = "ticket_price", nullable = false)
	private Double ticketPrice;

	@ElementCollection
	@CollectionTable(name = "keywords", joinColumns = @JoinColumn(name = "exhibition_id"))
	@Column(name = "keyword", nullable = false)
	private Set<String> keywords = new HashSet<>();

	@Transient
	private static double minTicketPrice;

	@ManyToMany(cascade = { CascadeType.DETACH
			, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "exhibition_rooms__exhibitions",
			   joinColumns = @JoinColumn(name = "exhibition_id"),
			   inverseJoinColumns = @JoinColumn(name = "exhibition_room_id"))
	private Set<ExhibitionRoom> exhibitionRooms = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.DETACH
			, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "exhibits_exhibitions",
		 	   joinColumns = @JoinColumn(name = "exhibition_id"),
		 	   inverseJoinColumns = @JoinColumn(name = "exhibit_id"))
	private Set<Exhibit> exhibits = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "curator_id", nullable = false)
	private Person curator;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "length_type_id")
	private ExhibitionLengthType exhibitionLengthType;

	public Exhibition() {

	}

	public Exhibition(String title, LocalDate openDate, LocalDate closeDate, double ticketPrice, Person curator,
			Set<String> keywords) {
		setTitle(title);
		setOpenDate(openDate);
		setCloseDate(closeDate);
		setTicketPrice(ticketPrice);
		setCurator(curator);
		setKeywords(keywords);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Set<String> getKeywords() {
		return new HashSet<String>(keywords);
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

	public void addKeyword(String keyword) {
		if (keywords.size() < 10) {
			if (!keywords.contains(keyword)) {
				keywords.add(keyword);
			}
		} else {
			throw new RuntimeException("You already added maximum number of keywords");
		}
	}

	public void removeKeyword(String keyword) {
		keywords.remove(keyword);
	}

	public static double getMinTicketPrice() {
		return minTicketPrice;
	}

	public static void setMinTicketPrice(double minTicketPrice) {
		if (minTicketPrice >= 0.0) {
			Exhibition.minTicketPrice = minTicketPrice;
		} else {
			throw new RuntimeException("Minimum ticket price can not be less than 0");
		}
	}

	public int getDuration() {
		return (int) Duration.between(openDate.atStartOfDay(), closeDate.atStartOfDay()).toDays();
	}

	public Person getCurator() {
		return curator;
	}

	public void setCurator(Person curator) {
		this.curator = curator;
	}

	public ExhibitionLengthType getExhibitionLengthType() {
		return exhibitionLengthType;
	}

	public void setExhibitionLengthType(ExhibitionLengthType exhibitionLengthType) {
		this.exhibitionLengthType = exhibitionLengthType;
	}

	public Set<ExhibitionRoom> getExhibitionRooms() {
		return new HashSet<>(exhibitionRooms);
	}

	public void setExhibitionRooms(Set<ExhibitionRoom> exhibitionRooms) {
		this.exhibitionRooms = new HashSet<>(exhibitionRooms);
	}

	public void addExhibitionRoom(ExhibitionRoom exhibitionRoom) {
		exhibitionRooms.add(exhibitionRoom);
	}

	public void removeExhibitionRoom(ExhibitionRoom exhibitionRoom) {
		exhibitionRooms.remove(exhibitionRoom);
	}

	public Set<Exhibit> getExhibits() {
		return new HashSet<>(exhibits);
	}

	public void setExhibits(Set<Exhibit> exhibits) {
		this.exhibits = exhibits;
	}

	public void addExhibit(Exhibit exhibit) {
		exhibits.add(exhibit);
	}

	public void removeExhibit(Exhibit exhibit) {
		exhibits.remove(exhibit);
	}

	@Override
	public String toString() {
		return "\nExhibition{" + "\n\tid=" + id + ", \n\ttitle=" + title + ", \n\topenDate=" + openDate
				+ ", \n\tcloseDate=" + closeDate + ", \n\tticketPrice=" + ticketPrice + ", \n\tcurator=" + curator
				//+ "\n\texhibits=" + exhibits + "\n\texhibitionRooms=" + exhibitionRooms 
				+ "\n" + '}';
	}
}
