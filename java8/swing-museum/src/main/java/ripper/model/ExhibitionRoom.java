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

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private RoomType roomType;

	@Column(name = "renovation_end_date")
	private LocalDate renovationEndDate;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "exhibition_id")
	private Exhibition exhibition;

	@OneToMany(mappedBy = "exhibitionRoom", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Set<Exhibit> exhibits = new HashSet<>();

	public ExhibitionRoom() {

	}

	public ExhibitionRoom(int roomNumber, double area) {
		setRoomType(RoomType.FREE);
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

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		if (roomType == null) {
			throw new RuntimeException("Room type ca not be null");
		}
		this.roomType = roomType;
	}

	public LocalDate getRenovationEndDate() {
		return renovationEndDate;
	}

	public void setRenovationEndDate(LocalDate renovationEndDate) {
		this.renovationEndDate = renovationEndDate;
	}

	public Exhibition getExhibition() {
		return exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		if (this.exhibition != exhibition) {
			if (exhibition != null) {
				markRoomAsUsed(exhibition);
			} else {
				markRoomAsFree();
			}

		}
	}

	public Set<Exhibit> getExhibits() {
		return new HashSet<>(exhibits);
	}

	public void setExhibits(Set<Exhibit> exhibits) {
		this.exhibits = exhibits;
	}

	public void addExhibit(Exhibit exhibit) {
		if (exhibit == null) {
			throw new RuntimeException("Exhibit can not be null");
		}
		if (exhibition == null) {
			throw new RuntimeException("There is not exhibition in this room");
		}
		if (!exhibits.contains(exhibit)) {
			if (exhibition.getExhibits().values().contains(exhibit)) {
				exhibits.add(exhibit);
				exhibit.setExhibitionRoom(this);
			} else {
				throw new RuntimeException("This exhibit is not part of current exhibition");
			}
		}
	}

	public void removeExhibit(Exhibit exhibit) {
		if (exhibits != null) {
			if (exhibits.contains(exhibit)) {
				exhibits.remove(exhibit);
				exhibit.setExhibitionRoom(null);
			}
		}
	}

	public void markRoomAsUsed(Exhibition exhibition) {
		if (roomType.equals(RoomType.RENOVATED)) {
			throw new RuntimeException("Room is renovated, it is not ready for exhibition yet");
		}
		if (this.exhibition != null) {
			this.exhibition.removeExhibitionRoom(this);
		}
		this.exhibition = exhibition;
		this.exhibition.addExhibitionRoom(this);
		setExhibits(new HashSet<>());
		setRoomType(RoomType.USED);
	}

	public void markRoomAsFree() {
		this.exhibition.removeExhibitionRoom(this);
		removeAllExhibitsFromRoom();
		this.exhibition = null;
		setRenovationEndDate(null);
		setRoomType(RoomType.FREE);
		
	}

	public void markRoomAsRenovated(LocalDate renovationEndDate) {
		markRoomAsFree();
		setRenovationEndDate(renovationEndDate);
		setRoomType(RoomType.RENOVATED);
	}

	private void removeAllExhibitsFromRoom() {
		Set<Exhibit> tempExhibits = getExhibits();
		for (Exhibit exhibit : tempExhibits) {
			exhibit.setExhibitionRoom(null);
		}
		setExhibits(null);
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
		return "ExhibitionRoom{" + "roomNumber=" + roomNumber + '}';
	}

	@Override
	public int compareTo(ExhibitionRoom o) {
		return roomNumber - o.roomNumber;
	}

}
