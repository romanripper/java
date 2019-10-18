package com.ripper.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ripper.entity.Booking;

public interface BookingService {
	
	void saveBooking(Booking booking);

	List<Booking> getBookingsForAllRooms(LocalDateTime dateFrom, LocalDateTime dateTo);

	List<Booking> getBookingsForRoom(String roomName, LocalDateTime dateFrom, LocalDateTime dateTo);

	List<Booking> getBookingsForUser(String login, LocalDateTime dateFrom, LocalDateTime dateTo);
	
}
