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
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "exhibition")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Exhibition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "open_date", nullable = false)
	private LocalDate openDate;

	@Column(name = "close_date", nullable = false)
	private LocalDate closeDate;

	@Column(name = "ticket_price", nullable = false)
	private double ticketPrice;

	@ElementCollection
	@CollectionTable(name = "keywords", joinColumns = @JoinColumn(name = "exhibition_id"))
	@Column(name = "keyword", nullable = false)
	private Set<String> keywords = new HashSet<>();

	@Transient
	private static double minTicketPrice;

	@OneToMany(mappedBy = "exhibition", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Set<ExhibitionRoom> exhibitionRooms = new TreeSet<>();

	@OneToMany(mappedBy = "exhibition", cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@MapKey
	private Map<Long, Exhibit> exhibits = new HashMap<>();

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH }
	, fetch = FetchType.LAZY)
	@JoinColumn(name = "curator_id", nullable = false)
	private Person curator;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "length_type_id")
	private ExhibitionLengthType exhibitionLengthType;

	public Exhibition() {

	}

	public Exhibition(String title, LocalDate openDate, LocalDate closeDate, double ticketPrice, Person curator, Set<String> keywords) {
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
		if (title != null) {
			this.title = title;
		} else {
			throw new RuntimeException("Title can not be null");
		}
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDate openDate) {
		if (openDate != null) {
			this.openDate = openDate;
		} else {
			throw new RuntimeException("Opening date can not be null");
		}
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDate closeDate) {
		if (closeDate != null) {
			this.closeDate = closeDate;
		} else {
			throw new RuntimeException("Closing date can not be null");
		}
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		if (ticketPrice >= minTicketPrice) {
			this.ticketPrice = ticketPrice;
		} else {
			throw new RuntimeException("Ticket price can not be less than minimum ticket price");
		}
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
		}else {
			throw new RuntimeException("You already added maximum number of keywords");
		}
	}

	public void removeKeyword(String keyword) {
		if (keywords.contains(keyword)) {
			keywords.remove(keyword);
		}
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
		if (this.curator != curator) {
			if (curator != null) {
				if (this.curator != null) {
					this.curator.removeExhibition(this);
				}
				this.curator = curator;
				this.curator.addExhibition(this);
			} else {
				if (this.curator != null) {
					this.curator.removeExhibition(this);
				}
				this.curator = curator;
			}

		}
	}

	public ExhibitionLengthType getExhibitionLengthType() {
		return exhibitionLengthType;
	}

	public void setExhibitionLengthType(ExhibitionLengthType exhibitionLengthType) {
		if (exhibitionLengthType == null) {
			throw new RuntimeException("Exhibition length type can not be null");
		}
		if (exhibitionLengthType.getExhibition() != this) {
			throw new RuntimeException("Exhibition length type of another exhibition");
		}
		this.exhibitionLengthType = exhibitionLengthType;
	}

	public Set<ExhibitionRoom> getExhibitionRooms() {
		return new TreeSet<>(exhibitionRooms);
	}

	public void setExhibitionRooms(Set<ExhibitionRoom> exhibitionRooms) {
		this.exhibitionRooms = new TreeSet<>(exhibitionRooms);
	}

	public void addExhibitionRoom(ExhibitionRoom exhibitionRoom) {
		if (!(exhibitionRooms.contains(exhibitionRoom))) {
			if (exhibitionRoom == null) {
				throw new RuntimeException("Exhibition room can not be null");
			}
			exhibitionRooms.add(exhibitionRoom);
			exhibitionRoom.setExhibition(this);
		}
	}

	public void removeExhibitionRoom(ExhibitionRoom exhibitionRoom) {
		if (exhibitionRooms.contains(exhibitionRoom)) {
			exhibitionRooms.remove(exhibitionRoom);
			exhibitionRoom.setExhibition(null);
		}
	}

	public Map<Long, Exhibit> getExhibits() {
		return new HashMap<>(exhibits);
	}

	public void setExhibits(Map<Long, Exhibit> exhibits) {
		this.exhibits = exhibits;
	}

	public void addExhibit(Exhibit exhibit) {
		if (exhibit == null) {
			throw new RuntimeException("Exhibit can not be null");
		}
		if (!exhibits.containsKey(exhibit.getId())) {
			exhibits.put(exhibit.getId(), exhibit);
			exhibit.setExhibition(this);
			System.out.println("Exhibition: addExhibit()");
		}
	}

	public void removeExhibit(Exhibit exhibit) {
		if (exhibits.containsKey(exhibit.getId())) {
			exhibits.remove(exhibit.getId());
			exhibit.setExhibition(null);
		}
	}

	public Exhibit findExhibitById(long id) {
		return exhibits.get(id);
	}

	@Override
	public String toString() {
		return "\nExhibition{" + "\n\tid=" + id + ", \n\ttitle=" + title + ", \n\topenDate=" + openDate
				+ ", \n\tcloseDate=" + closeDate + ", \n\tticketPrice=" + ticketPrice + "\n" + '}';
	}
}
