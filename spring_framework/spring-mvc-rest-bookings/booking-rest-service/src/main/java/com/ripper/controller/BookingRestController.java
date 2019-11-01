package com.ripper.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripper.request_wrapper.BookingRequestWrapper;
import com.ripper.response_wrapper.BookingResponseWrapper;
import com.ripper.service.BookingService;

@RestController
@RequestMapping("/api")
public class BookingRestController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/bookings")
	public ResponseEntity<BookingResponseWrapper> addBooking(@Valid @RequestBody BookingRequestWrapper requestWrapper) {

		BookingResponseWrapper response = bookingService.addBooking(requestWrapper);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/bookings")
	public ResponseEntity<List<BookingResponseWrapper>> getBookingsForAllRooms(
			@RequestBody BookingRequestWrapper requestWrapper) {

		List<BookingResponseWrapper> responseList = bookingService.getBookingsForAllRooms(requestWrapper);

		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

	@GetMapping("/bookings/room")
	public ResponseEntity<List<BookingResponseWrapper>> getBookingsForRoom(
			@RequestBody BookingRequestWrapper requestWrapper) {

		List<BookingResponseWrapper> responseList = bookingService.getBookingsForRoom(requestWrapper);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

	@GetMapping("/bookings/user")
	public ResponseEntity<List<BookingResponseWrapper>> getBookingForUser(
			@RequestBody BookingRequestWrapper requestWrapper) {
		
		List<BookingResponseWrapper> responseList = bookingService.getBookingsForUser(requestWrapper);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

}
