package com.ripper.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripper.dao.BookingDAO;
import com.ripper.entity.Booking;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingDAO bookingDAO;
	
	@Override
	@Transactional
	public void saveBooking(Booking booking) {
		bookingDAO.saveBooking(booking);
	}

	@Override
	@Transactional
	public List<Booking> getBookingsForAllRooms(LocalDateTime dateFrom, LocalDateTime dateTo) {
		return bookingDAO.getBookingsForAllRooms(dateFrom, dateTo);
	}

	@Override
	@Transactional
	public List<Booking> getBookingsForRoom(String roomName, LocalDateTime dateFrom, LocalDateTime dateTo) {
		return bookingDAO.getBookingsForRoom(roomName, dateFrom, dateTo);
	}

	@Override
	@Transactional
	public List<Booking> getBookingsForUser(String login, LocalDateTime dateFrom, LocalDateTime dateTo) {
		return bookingDAO.getBookingsForUser(login, dateFrom, dateTo);
	}

}
