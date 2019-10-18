package com.ripper.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ripper.config.AppConfig;
import com.ripper.config.SecurityConfig;
import com.ripper.entity.Role;
import com.ripper.entity.Room;
import com.ripper.entity.User;
import com.ripper.service.RoomService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class, SecurityConfig.class })
@WebAppConfiguration
public class RoomServiceTest {

	@Resource
	private RoomService roomService;

	@Test
	public void getRoomByNameTest() {
		Room testRoom = roomService.getRoomByName("Small Room");

		assertNotNull(testRoom);
		assertFalse(testRoom.isHasProjector());
	}

	@Test
	public void getAllRoomsTest() {

		List<Room> testRooms = roomService.getAllRooms();

		assertNotNull(testRooms);
		assertEquals(4,testRooms.size());
		assertEquals("Large Room", testRooms.get(0).getRoomName());
		assertEquals("Medium Room", testRooms.get(1).getRoomName());
	}

	@Test
	public void createRoomTest() {
		// Rename methods

		Room newRoom = new Room("New Room", "3rd floor", 35, true, null);
		roomService.saveRoom(newRoom);

		Room testRoom = roomService.getRoomByName("New Room");

		assertNotNull(testRoom);
		assertTrue(testRoom.isHasProjector());

	}

	@Test
	public void updateRoomTest() {

		Room roomToUpdate = roomService.getRoomByName("Medium Room");
		roomToUpdate.setNumberOfSeats(20);

		roomService.saveRoom(roomToUpdate);

		Room testRoom = roomService.getRoomByName("Medium Room");

		assertNotNull(testRoom);
		assertEquals(20, (int) testRoom.getNumberOfSeats());
	}

	@Test
	public void deleteRoomTest() {

		roomService.deleteRoom("Medium Room");

		Room testRoom = roomService.getRoomByName("Medium Room");

		assertNull(testRoom);

	}

}
