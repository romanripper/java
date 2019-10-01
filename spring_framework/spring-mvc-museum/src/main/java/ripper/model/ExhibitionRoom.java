package ripper.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "exhibition_room")
public class ExhibitionRoom implements Comparable<ExhibitionRoom> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "room_number", nullable = false)
	private int roomNumber;

	@Column(name = "area", nullable = false)
	private double area;

	@Column(name = "is_in_renovation", nullable = false)
	private boolean isInRenovation;

	@Column(name = "renovation_end_date")
	private LocalDate renovationEndDate;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "exhibition_rooms__exhibitions",
	   		   joinColumns = @JoinColumn(name = "exhibition_room_id"),
	   		   inverseJoinColumns = @JoinColumn(name = "exhibition_id"))
	private Set<Exhibition> exhibitions;

	@OneToMany(mappedBy = "exhibitionRoom", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Set<Exhibit> exhibits = new HashSet<>();

	public ExhibitionRoom() {

	}

	public ExhibitionRoom(int roomNumber, double area) {
		setRoomNumber(roomNumber);
		setArea(area);
	}

	public ExhibitionRoom(long id, int roomNumber, double area) {
		setId(id);
		setRoomNumber(roomNumber);
		setArea(area);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public boolean isInRenovation() {
		return isInRenovation;
	}

	public void setInRenovation(boolean isInRenovation) {
		this.isInRenovation = isInRenovation;
	}

	public LocalDate getRenovationEndDate() {
		return renovationEndDate;
	}

	public void setRenovationEndDate(LocalDate renovationEndDate) {
		this.renovationEndDate = renovationEndDate;
	}

	public Set<Exhibition> getExhibitions() {
		return exhibitions;
	}

	public void setExhibitions(Set<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}
	
	public void addExhibition(Exhibition exhibition) {
		exhibitions.add(exhibition);
	}
	
	public void removeExhibition(Exhibition exhibition) {
		exhibitions.remove(exhibition);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + roomNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExhibitionRoom other = (ExhibitionRoom) obj;
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (roomNumber != other.roomNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExhibitionRoom [id=" + id + ", roomNumber=" + roomNumber + "]";
	}

	@Override
	public int compareTo(ExhibitionRoom o) {
		return roomNumber - o.roomNumber;
	}

}
