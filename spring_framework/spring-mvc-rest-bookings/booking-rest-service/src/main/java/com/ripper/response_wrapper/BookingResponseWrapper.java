package com.ripper.response_wrapper;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ripper.utils.Constants;

public class BookingResponseWrapper {

	private String roomName;
	private String userFirstName;
	private String userLastName;

	@JsonFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@DateTimeFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	private LocalDateTime dateFrom;

	@JsonFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@DateTimeFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	private LocalDateTime dateTo;

	public BookingResponseWrapper() {
	}

	public BookingResponseWrapper(String roomName, String userFirstName, String userLastName, LocalDateTime dateFrom,
			LocalDateTime dateTo) {
		this.roomName = roomName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
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
