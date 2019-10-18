package com.ripper.dao;

import java.util.List;

import com.ripper.entity.Room;

public interface RoomDAO {

	List<Room> getAllRooms();
	
	void saveRoom(Room room);
	
	void deleteRoom(String roomName);
	
	Room getRoomByName(String roomName);
	
}
