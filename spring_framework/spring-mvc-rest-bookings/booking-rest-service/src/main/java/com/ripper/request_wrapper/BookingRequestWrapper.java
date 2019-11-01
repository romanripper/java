package com.ripper.request_wrapper;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ripper.utils.Constants;
import com.ripper.validation.ValidationConstants;
import com.ripper.validation.custom_annotation.ConsistentDateTimes;

@ConsistentDateTimes
public class BookingRequestWrapper {

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Future(message = ValidationConstants.FUTURE_MESSAGE)
	@JsonFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@DateTimeFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	private LocalDateTime dateFrom;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Future(message = ValidationConstants.FUTURE_MESSAGE)
	@JsonFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	@DateTimeFormat(pattern = Constants.FORMAT_LOCAL_DATE_TIME)
	private LocalDateTime dateTo;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_LOGIN, min = ValidationConstants.SIZE_MIN_LOGIN, message = ValidationConstants.SIZE_MESSAGE)
	private String login;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_ROOM_NAME, min = ValidationConstants.SIZE_MIN_ROOM_NAME, message = ValidationConstants.SIZE_MESSAGE)
	private String roomName;

	public BookingRequestWrapper() {

	}

	public BookingRequestWrapper(LocalDateTime dateFrom, LocalDateTime dateTo, String login, String roomName) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.login = login;
		this.roomName = roomName;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
