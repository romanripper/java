package com.ripper.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ripper.utils.Constants;
import com.ripper.utils.JPA_Constants;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = JPA_Constants.JOIN_COLUMN_NAME_USER)
	private User user;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = JPA_Constants.JOIN_COLUMN_NAME_ROOM)
	private Room room;

	@JsonFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@DateTimeFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@Column(name = JPA_Constants.COLUMN_NAME_DATE_FROM, nullable = false)
	private LocalDateTime dateFrom;

	@JsonFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@DateTimeFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@Column(name = JPA_Constants.COLUMN_NAME_DATE_TO, nullable = false)
	private LocalDateTime dateTo;

	public Booking() {

	}

	public Booking(User user, Room room, LocalDateTime dateFrom, LocalDateTime dateTo) {
		this.user = user;
		this.room = room;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	public Booking(int id, User user, Room room, LocalDateTime dateFrom, LocalDateTime dateTo) {
		this.id = id;
		this.user = user;
		this.room = room;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDateTime getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDateTime dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDateTime getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDateTime dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", user=" + user.getLogin() + ", room=" + room.getRoomName() + ", dateFrom=" + dateFrom + ", dateTo="
				+ dateTo + "]";
	}

	
}
