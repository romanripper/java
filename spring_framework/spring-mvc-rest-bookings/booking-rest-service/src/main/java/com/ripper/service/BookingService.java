package com.ripper.service;

import java.util.List;

import com.ripper.request_wrapper.BookingRequestWrapper;
import com.ripper.response_wrapper.BookingResponseWrapper;

public interface BookingService {
	
	BookingResponseWrapper addBooking(BookingRequestWrapper requestWrapper);

	List<BookingResponseWrapper> getBookingsForAllRooms(BookingRequestWrapper requestWrapper);

	List<BookingResponseWrapper> getBookingsForRoom(BookingRequestWrapper requestWrapper);

	List<BookingResponseWrapper> getBookingsForUser(BookingRequestWrapper requestWrapper);
	
}
