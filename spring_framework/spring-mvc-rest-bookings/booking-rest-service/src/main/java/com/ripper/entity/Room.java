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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ripper.utils.Constants;
import com.ripper.utils.JPA_Constants;
import com.ripper.validation.ValidationConstants;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_ROOM_NAME, min = ValidationConstants.SIZE_MIN_ROOM_NAME, message = ValidationConstants.SIZE_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_ROOM_NAME, nullable = false, unique = true, length = JPA_Constants.COLUMN_LENGTH_ROOM_NAME)
	private String roomName;

	@Size(max = ValidationConstants.SIZE_MAX_LOCATION_DECRIPTION, min = ValidationConstants.SIZE_MIN_LOCATION_DECRIPTION, message = ValidationConstants.SIZE_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_LOCATION_DESCRIPTION, nullable = true, length = JPA_Constants.COLUMN_LENGTH_LOCATION_DESCRIPTION)
	private String locationDescription;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Max(value = ValidationConstants.MAX_NUMBER_OF_SEATS, message = ValidationConstants.MAX_MESSAGE)
	@Min(value = ValidationConstants.MIN_NUMBER_OF_SEATS, message = ValidationConstants.MIN_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_NUMBER_OF_SEATS, nullable = false)
	private Integer numberOfSeats;

	@Column(name = JPA_Constants.COLUMN_NAME_HAS_PROJECTOR, nullable = false)
	private Boolean hasProjector;

	// pattern matches phone number format(dd-dd-dd-dd)
	@Pattern(regexp = Constants.PATTERN_PHONE_NUMBER, message = ValidationConstants.PATTERN_MESSAGE_PHONE_NUMBER)
	@Column(name = JPA_Constants.COLUMN_NAME_PHONE_NUBMER, nullable = true, length = JPA_Constants.COLUMN_LENGTH_PHONE_NUMBER)
	private String phoneNumber;

	@JsonIgnore
	@OneToMany(mappedBy = JPA_Constants.MAPPED_BY_ROOM, cascade = CascadeType.ALL)
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
		this.roomName = roomName;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
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
		this.phoneNumber = phoneNumber;
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
