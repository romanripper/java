package com.ripper.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.ripper.entity.Room;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.DataExistsException;
import com.ripper.exception.NotFoundException;
import com.ripper.repository.RoomRepository;
import com.ripper.validation.ValidationHandler;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	@Override
	@Transactional
	public Room addRoom(Room room) {

		if (roomNameExists(room.getRoomName())) {
			throw new DataExistsException("Room with such name already exists in the system");
		}

		// in case id in JSON is provided we set id to 0
		// this will force hibernate to create new room instead of update
		room.setId(0);

		Room savedRoom = roomRepository.save(room);

		return savedRoom;
	}

	@Override
	@Transactional
	public Room updateRoom(Room room, BindingResult bindingResult) {

		// we have to exclude NotNull validation error because properties
		// could be null in update call
		ValidationHandler.validateWithoutNotNullErrorCode(bindingResult);
		
		// provided room name is null or not provided at all
		if (room.getRoomName() == null) {
			throw new BadRequestException("Room name is missing or not valid");
		}

		Room roomToUpdate = roomRepository.findByName(room.getRoomName());

		// there is no room with such name
		if (roomToUpdate == null) {
			throw new NotFoundException("Room not found with name " + room.getRoomName());
		}

		// if locationDescription is provided we have to update it in our roomToUpdate
		if (room.getLocationDescription() != null) {
			roomToUpdate.setLocationDescription(room.getLocationDescription());
		}

		// if hasProjector is provided we have to update it in our roomToUpdate
		if (room.isHasProjector() != null) {
			roomToUpdate.setHasProjector(room.isHasProjector());
		}

		// if numberOfSeats is provided we have to update it in our roomToUpdate
		if (room.getNumberOfSeats() != null) {
			roomToUpdate.setNumberOfSeats(room.getNumberOfSeats());
		}

		// if phoneNumber is provided we have to update it in our roomToUpdate
		if (room.getPhoneNumber() != null) {
			roomToUpdate.setPhoneNumber(room.getPhoneNumber());
		}

		Room updatedRoom = roomRepository.save(roomToUpdate);
		
		return updatedRoom;
	}

	@Override
	@Transactional
	public void deleteRoom(String roomName) {
		
		if (roomName == null) {
			throw new BadRequestException("Room name is missing or not valid");
		}
		if(!roomNameExists(roomName)) {
			throw new NotFoundException("Room not found with name " + roomName);
		}
		roomRepository.deleteByRoomName(roomName);
	}

	@Override
	public Room getRoomByName(String roomName) {
		return roomRepository.findByName(roomName);
	}
	
	@Override
	public boolean roomNameExists(String name) {
		boolean result = false;
		if (roomRepository.findByName(name) != null)
			result = true;
		return result;

	}

}
