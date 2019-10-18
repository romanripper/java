package com.ripper.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripper.entity.Booking;
import com.ripper.entity.Room;
import com.ripper.entity.User;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.InvalidDataPassedException;
import com.ripper.exception.NotFoundException;
import com.ripper.request_wrapper.BookingRequestWrapper;
import com.ripper.response.ControllerResponse;
import com.ripper.response_wrapper.BookingResponseWrapper;
import com.ripper.service.BookingService;
import com.ripper.service.RoomService;
import com.ripper.service.UserService;
import com.ripper.utils.MyUtils;

@RestController
@RequestMapping("/api")
public class BookingRestController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@PostMapping("/bookings")
	public ResponseEntity<BookingResponseWrapper> addBooking(@Valid @RequestBody BookingRequestWrapper requestWrapper,
			BindingResult bindingResult) {

		// validation of passed data
		if (bindingResult.hasErrors()) {

			List<String> errors = bindingResult.getFieldErrors().stream()
					.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
					.collect(Collectors.toList());

			if (bindingResult.hasGlobalErrors()) {
				bindingResult.getGlobalErrors().stream().forEach(error -> errors.add(error.getDefaultMessage()));
			}

			throw new InvalidDataPassedException(errors);
		}

		User user = userService.getUserByLogin(requestWrapper.getLogin());
		Room room = roomService.getRoomByName(requestWrapper.getRoomName());

		// there is no user with such login
		if (user == null) {
			throw new NotFoundException("User not found with login - " + requestWrapper.getLogin());
		}
		// there is no room with such name
		if (room == null) {
			throw new NotFoundException("Room not found with name - " + requestWrapper.getRoomName());
		}
		
		List<Booking> bookingsOfRoom = bookingService.getBookingsForRoom(room.getRoomName(), requestWrapper.getDateFrom(), requestWrapper.getDateTo());
		if(!bookingsOfRoom.isEmpty()) {
			throw new BadRequestException("Room with name - " + room.getRoomName() + " is already booked for requested time frame");
		}

		Booking booking = new Booking(0, user, room, requestWrapper.getDateFrom(), requestWrapper.getDateTo());

		bookingService.saveBooking(booking);

		return new ResponseEntity<>(new BookingResponseWrapper(booking.getRoom().getRoomName(), booking.getUser().getFirstName(),
				booking.getUser().getLastName(), booking.getDateFrom(), booking.getDateTo()), HttpStatus.OK);
	}

	@GetMapping("/bookings")
	public ResponseEntity<List<BookingResponseWrapper>> getBookingsForAllRooms(
			@RequestBody BookingRequestWrapper requestWrapper) {

		// if non of the dates are provided
		if (requestWrapper.getDateTo() == null && requestWrapper.getDateFrom() == null) {
			throw new BadRequestException("At least on one of the dates must be provided");
		}

		List<Booking> retreivedBookings = bookingService.getBookingsForAllRooms(requestWrapper.getDateFrom(),
				requestWrapper.getDateTo());

		return new ResponseEntity<>(MyUtils.prepareBookingResposeList(retreivedBookings), HttpStatus.OK);
	}

	@GetMapping("/bookings/room")
	public ResponseEntity<List<BookingResponseWrapper>> getBookingsForRoom(
			@RequestBody BookingRequestWrapper requestWrapper) {

		// if non of the dates are provided
		if (requestWrapper.getDateTo() == null && requestWrapper.getDateFrom() == null) {
			throw new BadRequestException("At least on one of the dates must be provided");
		}

		// if room name is not provided
		if (requestWrapper.getRoomName() == null) {
			throw new BadRequestException("Room name must be provided");
		}

		// there is no room with such name
		if (roomService.getRoomByName(requestWrapper.getRoomName()) == null) {
			throw new NotFoundException("Room not found with name - " + requestWrapper.getRoomName());
		}

		List<Booking> retreivedBookings = bookingService.getBookingsForRoom(requestWrapper.getRoomName(),
				requestWrapper.getDateFrom(), requestWrapper.getDateTo());
		return new ResponseEntity<>(MyUtils.prepareBookingResposeList(retreivedBookings), HttpStatus.OK);
	}

	@GetMapping("/bookings/user")
	public ResponseEntity<List<BookingResponseWrapper>> getBookingForUser(
			@RequestBody BookingRequestWrapper requestWrapper) {
		
		// if non of the dates are provided
		if (requestWrapper.getDateTo() == null && requestWrapper.getDateFrom() == null) {
			throw new BadRequestException("At least on one of the dates must be provided");
		}

		// if login is not provided
		if (requestWrapper.getLogin() == null) {
			throw new BadRequestException("Login must be provided");
		}

		// there is no user with such login
		if (userService.getUserByLogin(requestWrapper.getLogin()) == null) {
			throw new NotFoundException("User not found with login - " + requestWrapper.getLogin());
		}

		List<Booking> retreivedBookings = bookingService.getBookingsForUser(requestWrapper.getLogin(),
				requestWrapper.getDateFrom(), requestWrapper.getDateTo());
		return new ResponseEntity<>(MyUtils.prepareBookingResposeList(retreivedBookings), HttpStatus.OK);
	}

}
