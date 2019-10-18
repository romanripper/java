package com.ripper.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripper.dao.RoomDAO;
import com.ripper.entity.Room;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDAO roomDAO;
	
	@Override
	@Transactional
	public List<Room> getAllRooms() {
		return roomDAO.getAllRooms();
	}

	@Override
	@Transactional
	public void saveRoom(Room room) {
		roomDAO.saveRoom(room);
	}

	@Override
	@Transactional
	public void deleteRoom(String roomName) {
		roomDAO.deleteRoom(roomName);
	}

	@Override
	@Transactional
	public Room getRoomByName(String roomName) {
		return roomDAO.getRoomByName(roomName);
	}

}
