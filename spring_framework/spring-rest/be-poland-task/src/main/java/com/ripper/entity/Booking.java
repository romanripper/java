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
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ripper.utils.MyUtils;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "room_id")
	private Room room;

	@JsonFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	@DateTimeFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	@Column(name = "date_from", nullable = false)
	private LocalDateTime dateFrom;

	@JsonFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	@DateTimeFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	@Column(name = "date_to", nullable = false)
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

}
