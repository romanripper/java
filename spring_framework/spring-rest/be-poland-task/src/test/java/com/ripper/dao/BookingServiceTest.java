package com.ripper.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ripper.config.AppConfig;
import com.ripper.config.SecurityConfig;
import com.ripper.entity.Booking;
import com.ripper.entity.Room;
import com.ripper.entity.User;
import com.ripper.service.BookingService;
import com.ripper.service.RoomService;
import com.ripper.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class, SecurityConfig.class })
@WebAppConfiguration
public class BookingServiceTest {

	@Resource
	private BookingService bookingService;

	@Resource
	private UserService userService;

	@Resource
	private RoomService roomService;
	
	@Test
	public void getBookingsForAllRoomsTest() {
		//21 12:00 --- 23 16.30	
		LocalDateTime testDateFrom1 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo1 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings1 = bookingService.getBookingsForAllRooms(testDateFrom1, testDateTo1);
		
		assertNotNull(testBookings1);
		assertEquals(1, testBookings1.size());
		assertEquals("jdoe", testBookings1.get(0).getUser().getLogin());
		assertEquals("Small Room", testBookings1.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom2 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo2 = LocalDateTime.of(2019, 10, 21, 15, 30);
	
		List<Booking> testBookings2 = bookingService.getBookingsForAllRooms(testDateFrom2, testDateTo2);
		
		assertNotNull(testBookings2);
		assertEquals(2, testBookings2.size());
		
		LocalDateTime testDateFrom3 = LocalDateTime.of(2019, 10, 22, 15, 30);
		LocalDateTime testDateTo3 = LocalDateTime.of(2019, 10, 23, 18, 30);
	
		List<Booking> testBookings3 = bookingService.getBookingsForAllRooms(testDateFrom3, testDateTo3);
		
		assertNotNull(testBookings3);
		assertEquals(1, testBookings3.size());
		assertEquals("jsmith", testBookings3.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings3.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom4 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo4 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings4 = bookingService.getBookingsForAllRooms(testDateFrom4, testDateTo4);
		
		assertNotNull(testBookings4);
		assertEquals(1, testBookings4.size());
		assertEquals("jdoe", testBookings1.get(0).getUser().getLogin());
		assertEquals("Small Room", testBookings1.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom5 = LocalDateTime.of(2019, 10, 20, 15, 30);
		LocalDateTime testDateTo5 = LocalDateTime.of(2019, 10, 26, 18, 30);
	
		List<Booking> testBookings5 = bookingService.getBookingsForAllRooms(testDateFrom5, testDateTo5);
		
		assertNotNull(testBookings5);
		assertEquals(1, testBookings5.size());
		assertEquals("jsmith", testBookings5.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings5.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom6 = LocalDateTime.of(2019, 10, 21, 15, 30);
		LocalDateTime testDateTo6 = LocalDateTime.of(2019, 10, 23, 12, 30);
	
		List<Booking> testBookings6 = bookingService.getBookingsForAllRooms(testDateFrom6, testDateTo6);
		
		assertNotNull(testBookings6);
		assertEquals(1, testBookings6.size());
		assertEquals("jsmith", testBookings6.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings6.get(0).getRoom().getRoomName());
		
	}
	
	@Test
	public void getBookingsForRoomTest() {
		
		String roomName = "Large Room";
		LocalDateTime testDateFrom1 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo1 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings1 = bookingService.getBookingsForRoom(roomName, testDateFrom1, testDateTo1);
		
		assertNotNull(testBookings1);
		assertEquals(0, testBookings1.size());
		
		LocalDateTime testDateFrom2 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo2 = LocalDateTime.of(2019, 10, 21, 15, 30);
	
		List<Booking> testBookings2 = bookingService.getBookingsForRoom(roomName, testDateFrom2, testDateTo2);
		
		assertNotNull(testBookings2);
		assertEquals(1, testBookings2.size());
		assertEquals("jsmith", testBookings2.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings2.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom3 = LocalDateTime.of(2019, 10, 22, 15, 30);
		LocalDateTime testDateTo3 = LocalDateTime.of(2019, 10, 23, 18, 30);
	
		List<Booking> testBookings3 = bookingService.getBookingsForRoom(roomName, testDateFrom3, testDateTo3);
		
		assertNotNull(testBookings3);
		assertEquals(1, testBookings3.size());
		assertEquals("jsmith", testBookings3.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings3.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom4 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo4 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings4 = bookingService.getBookingsForRoom(roomName, testDateFrom4, testDateTo4);
		
		assertNotNull(testBookings4);
		assertEquals(0, testBookings4.size());
		
		LocalDateTime testDateFrom5 = LocalDateTime.of(2019, 10, 20, 15, 30);
		LocalDateTime testDateTo5 = LocalDateTime.of(2019, 10, 26, 18, 30);
	
		List<Booking> testBookings5 = bookingService.getBookingsForRoom(roomName, testDateFrom5, testDateTo5);
		
		assertNotNull(testBookings5);
		assertEquals(1, testBookings5.size());
		assertEquals("jsmith", testBookings5.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings5.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom6 = LocalDateTime.of(2019, 10, 21, 15, 30);
		LocalDateTime testDateTo6 = LocalDateTime.of(2019, 10, 23, 12, 30);
	
		List<Booking> testBookings6 = bookingService.getBookingsForRoom(roomName, testDateFrom6, testDateTo6);
		
		assertNotNull(testBookings6);
		assertEquals(1, testBookings6.size());
		assertEquals("jsmith", testBookings6.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings6.get(0).getRoom().getRoomName());
	}
	
	@Test
	public void getBookingsForUserTest() {
		
		String login = "jsmith";
		LocalDateTime testDateFrom1 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo1 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings1 = bookingService.getBookingsForUser(login, testDateFrom1, testDateTo1);
		
		assertNotNull(testBookings1);
		assertEquals(0, testBookings1.size());
		
		LocalDateTime testDateFrom2 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo2 = LocalDateTime.of(2019, 10, 21, 15, 30);
	
		List<Booking> testBookings2 = bookingService.getBookingsForUser(login, testDateFrom2, testDateTo2);
		
		assertNotNull(testBookings2);
		assertEquals(1, testBookings2.size());
		assertEquals("jsmith", testBookings2.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings2.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom3 = LocalDateTime.of(2019, 10, 22, 15, 30);
		LocalDateTime testDateTo3 = LocalDateTime.of(2019, 10, 23, 18, 30);
	
		List<Booking> testBookings3 = bookingService.getBookingsForUser(login, testDateFrom3, testDateTo3);
		
		assertNotNull(testBookings3);
		assertEquals(1, testBookings3.size());
		assertEquals("jsmith", testBookings3.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings3.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateFrom4 = LocalDateTime.of(2019, 10, 18, 15, 30);
		LocalDateTime testDateTo4 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings4 = bookingService.getBookingsForUser(login, testDateFrom4, testDateTo4);
		
		assertNotNull(testBookings4);
		assertEquals(0, testBookings4.size());
		
		LocalDateTime testDateFrom5 = LocalDateTime.of(2019, 10, 20, 15, 30);
	
		List<Booking> testBookings5 = bookingService.getBookingsForUser(login, testDateFrom5, null);
		
		assertNotNull(testBookings5);
		assertEquals(1, testBookings5.size());
		assertEquals("jsmith", testBookings5.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings5.get(0).getRoom().getRoomName());
		
		LocalDateTime testDateTo6 = LocalDateTime.of(2019, 10, 23, 12, 30);
	
		List<Booking> testBookings6 = bookingService.getBookingsForUser(login, null, testDateTo6);
		
		assertNotNull(testBookings6);
		assertEquals(1, testBookings6.size());
		assertEquals("jsmith", testBookings6.get(0).getUser().getLogin());
		assertEquals("Large Room", testBookings6.get(0).getRoom().getRoomName());
	}
	
	

	@Test
	public void createBookingTest() {
		User testUser = userService.getUserByLogin("jdoe");
		Room testRoom = roomService.getRoomByName("Large Room");

		LocalDateTime testDateFrom = LocalDateTime.of(2019, 10, 23, 15, 30);
		LocalDateTime testDateto = LocalDateTime.of(2019, 10, 24, 17, 30);
		
		Booking testBooking = new Booking(testUser, testRoom, testDateFrom, testDateto);
		
		bookingService.saveBooking(testBooking);
		
		List<Booking> retrievedBookings = bookingService.getBookingsForUser(testUser.getLogin(), testDateFrom, testDateto);
		
		assertNotNull(retrievedBookings);
		assertEquals(1, retrievedBookings.size());
		
	}
}
