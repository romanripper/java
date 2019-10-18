package com.ripper.service;

import java.util.List;

import com.ripper.entity.Room;

public interface RoomService {

	List<Room> getAllRooms();
	
	void saveRoom(Room room);
	
	void deleteRoom(String roomName);
	
	Room getRoomByName(String roomName);
	
}
