package com.ripper.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ripper.entity.Booking;
import com.ripper.entity.Room;
import com.ripper.entity.User;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.NotFoundException;
import com.ripper.jpa_specification.BookingSpecifications;
import com.ripper.repository.BookingRepository;
import com.ripper.request_wrapper.BookingRequestWrapper;
import com.ripper.response_wrapper.BookingResponseWrapper;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public BookingResponseWrapper addBooking(BookingRequestWrapper requestWrapper) {

		System.out.println("\n\n\n\n"
				+ requestWrapper.getLogin() + " " + requestWrapper.getRoomName()
				+ "\n\n\n\n");
		
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
		
		List<BookingResponseWrapper> bookingsOfRoom = getBookingsForRoom(requestWrapper);
		if(!bookingsOfRoom.isEmpty()) {
			throw new BadRequestException("Room with name - " + room.getRoomName() + " is already booked for requested time frame");
		}

		Booking booking = new Booking(0, user, room, requestWrapper.getDateFrom(), requestWrapper.getDateTo());
		
		bookingRepository.save(booking);
		
		return new BookingResponseWrapper(booking.getRoom().getRoomName(), booking.getUser().getFirstName(),
				booking.getUser().getLastName(), booking.getDateFrom(), booking.getDateTo());
	}

	@Override
	public List<BookingResponseWrapper> getBookingsForAllRooms(BookingRequestWrapper requestWrapper) {

		checkIfAtLeastOneDateNotNull(requestWrapper);

		List<Booking> retreivedBookings = bookingRepository.findAll(Specification
				.where(BookingSpecifications.betweenDates(requestWrapper.getDateFrom(), requestWrapper.getDateTo())));

		return prepareBookingResposeList(retreivedBookings);
	}

	@Override
	public List<BookingResponseWrapper> getBookingsForRoom(BookingRequestWrapper requestWrapper) {
		
		checkIfAtLeastOneDateNotNull(requestWrapper);

		// if room name is not provided
		if (requestWrapper.getRoomName() == null) {
			throw new BadRequestException("Room name must be provided");
		}

		if (!roomService.roomNameExists(requestWrapper.getRoomName())) {
			throw new NotFoundException("Room not found with name " + requestWrapper.getRoomName());
		}
		
		System.out.println("\n\n\n\n"
				+ ""
				+ "\n\n\n\n");

		List<Booking> retreivedBookings = bookingRepository.findAll(Specification
				.where(BookingSpecifications.betweenDates(requestWrapper.getDateFrom(), requestWrapper.getDateTo()))
				.and(BookingSpecifications.reservedRoom(requestWrapper.getRoomName())));
		
		return prepareBookingResposeList(retreivedBookings);
	}

	@Override
	public List<BookingResponseWrapper> getBookingsForUser(BookingRequestWrapper requestWrapper) {
		
		checkIfAtLeastOneDateNotNull(requestWrapper);
		
		if (requestWrapper.getLogin() == null) {
			throw new BadRequestException("Login must be provided");
		}

		if (!userService.loginExists(requestWrapper.getLogin())) {
			throw new NotFoundException("User not found with login - " + requestWrapper.getLogin());
		}
		
		
		List<Booking> retreivedBookings = bookingRepository.findAll(Specification.where(BookingSpecifications.betweenDates(requestWrapper.getDateFrom(), requestWrapper.getDateTo()))
				.and(BookingSpecifications.reservedByUser(requestWrapper.getLogin())));
		
		return prepareBookingResposeList(retreivedBookings);
	}

	private void checkIfAtLeastOneDateNotNull(BookingRequestWrapper requestWrapper) {
		if (requestWrapper.getDateTo() == null && requestWrapper.getDateFrom() == null) {
			throw new BadRequestException("At least on one of the dates must be provided");
		}
	}

	// prepares BookingResponseList based on Bookings
	private List<BookingResponseWrapper> prepareBookingResposeList(List<Booking> retreivedBookings) {
		List<BookingResponseWrapper> result = new ArrayList<>();
		retreivedBookings.stream()
				.forEach(booking -> result.add(
						new BookingResponseWrapper(booking.getRoom().getRoomName(), booking.getUser().getFirstName(),
								booking.getUser().getLastName(), booking.getDateFrom(), booking.getDateTo())));
		return result;
	}

}
