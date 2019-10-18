package com.ripper.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripper.entity.Room;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.DataExistsException;
import com.ripper.exception.InvalidDataPassedException;
import com.ripper.exception.NotFoundException;
import com.ripper.response.ControllerResponse;
import com.ripper.service.RoomService;
import com.ripper.utils.ValidationErrorCode;

@RestController
@RequestMapping("/api")
public class RoomRestController {

	@Autowired
	private RoomService roomService;

	@GetMapping("/rooms")
	public ResponseEntity<List<Room>> getAllRooms() {
		return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
	}

	@PostMapping("/rooms")
	public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room, BindingResult bindingResult) {

		// validation of passed data
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream()
					.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
					.collect(Collectors.toList());

			if (!errors.isEmpty())
				throw new InvalidDataPassedException(errors);
		}

		// there is a room with such name
		if (roomService.getRoomByName(room.getRoomName()) != null) {
			throw new DataExistsException("Room with such name already exists in the system");
		}

		// in case id in JSON is provided we set id to 0
		// this will force hibernate to create new room instead of update
		room.setId(0);

		roomService.saveRoom(room);

		return new ResponseEntity<>(room, HttpStatus.OK);
	}

	@PutMapping("/rooms")
	public ResponseEntity<Room> updateRoom(@Valid @RequestBody Room room, BindingResult bindingResult) {

		// validation of passed data
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {

				// we have to exclude NotNull validation error because in update call properties
				// could be null
				if (ValidationErrorCode.fromString(fieldError.getCode()) != ValidationErrorCode.NOT_NULL) {
					errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
				}
			}
			if (!errors.isEmpty())
				throw new InvalidDataPassedException(errors);
		}

		// provided room name is null or not provided at all
		if (room.getRoomName() == null) {
			throw new BadRequestException("Room name is missing or not valid");
		}

		Room roomToUpdate = roomService.getRoomByName(room.getRoomName());

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

		roomService.saveRoom(roomToUpdate);

		return new ResponseEntity<>(roomToUpdate, HttpStatus.OK);
	}

	@DeleteMapping("/rooms")
	public ResponseEntity<ControllerResponse> deleteRoom(@RequestBody Room room) {

		// provided room name is null or not provided at all
		if (room.getRoomName() == null) {
			throw new BadRequestException("Error - Room name is missing or not valid");
		}

		// there is no room with such name
		if (roomService.getRoomByName(room.getRoomName()) == null) {
			throw new NotFoundException("Error - Room not found with name " + room.getRoomName());
		}

		roomService.deleteRoom(room.getRoomName());

		return new ResponseEntity<>(new ControllerResponse(HttpStatus.OK.value(),
				"Room deleted with name " + room.getRoomName(), System.currentTimeMillis()), HttpStatus.OK);
	}

}
