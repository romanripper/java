package com.ripper.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ripper.utils.MyUtils;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "is required")
	@Size(max = 50, min = 1, message = "maximum length is 50 characters")
	@Column(name = "room_name", nullable = false, unique = true, length = 50)
	private String roomName;

	@Size(max = 256, min = 1, message = "maximum length is 256 characters")
	@Column(name = "location_description", nullable = true, length = 256)
	private String locationDescription;

	@NotNull(message = "is required")
	@Max(value = 100, message = "maximum value is 100")
	@Min(value = 0, message = "maximum value is 0")
	@Column(name = "number_of_seats", nullable = false)
	private Integer numberOfSeats;

	@Column(name = "has_projector", nullable = false)
	private Boolean hasProjector;

	// pattern matches phone number format(dd-dd-dd-dd)
	@Pattern(regexp = MyUtils.PHONE_NUMBER_PATTERN, message = "invalid format. Has to be in format 'dd-dd-dd-dd'")
	@Size(max = 100, min = 1, message = "maximum length is 100 characters")
	@Column(name = "phone_nubmer", nullable = true, length = 50)
	private String phoneNumber;

	@JsonIgnore
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private Set<Booking> bookings;

	public Room() {

	}

	public Room(String roomName, String locationDescription, Integer numberOfSeats, Boolean hasProjector,
			String phoneNumber) {
		this.roomName = roomName;
		this.locationDescription = locationDescription;
		this.numberOfSeats = numberOfSeats;
		this.hasProjector = hasProjector;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		// we trim string to delete trailing and leading whitespaces
		this.roomName = roomName.trim();
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		// we trim string to delete trailing and leading whitespaces
		this.locationDescription = locationDescription.trim();
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Boolean isHasProjector() {
		return hasProjector;
	}

	public void setHasProjector(Boolean hasProjector) {
		this.hasProjector = hasProjector;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		// we trim string to delete trailing and leading whitespaces
		this.phoneNumber = phoneNumber.trim();
	}

	public Set<Booking> getBookings() {
		return new HashSet<>(bookings);
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	// if boolean not set then default value is false
	@PrePersist
	public void prepareHasProjectorAttribute() {
		if (hasProjector == null) {
			setHasProjector(false);
		}
	}

}
