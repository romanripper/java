package com.ripper.request_wrapper;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ripper.utils.MyUtils;
import com.ripper.validation.ConsistentDateTimes;

@ConsistentDateTimes
public class BookingRequestWrapper {

	@NotNull(message = "is required")
	@Future(message = "must be in future")
	@JsonFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	@DateTimeFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	private LocalDateTime dateFrom;

	@NotNull(message = "is required")
	@Future(message = "must be in future")
	@JsonFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	@DateTimeFormat(pattern = MyUtils.LOCAL_DATE_TIME_FORMAT)
	private LocalDateTime dateTo;

	@NotNull(message = "is required")
	@Size(max = 100, min = 1, message = "maximum length is 100 characters")
	private String login;

	@NotNull(message = "is required")
	@Size(max = 50, min = 1, message = "maximum length is 50 characters")
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
