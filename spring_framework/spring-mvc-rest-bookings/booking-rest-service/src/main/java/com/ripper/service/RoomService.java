package com.ripper.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.ripper.entity.Room;

public interface RoomService {

	List<Room> getAllRooms();
	
	Room addRoom(Room room);
	
	Room updateRoom(Room room, BindingResult bindingResult);
	
	void deleteRoom(String roomName);
	
	Room getRoomByName(String roomName);
	
	boolean roomNameExists(String name);
	
}
