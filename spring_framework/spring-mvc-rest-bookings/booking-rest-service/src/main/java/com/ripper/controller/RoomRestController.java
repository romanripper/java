package com.ripper.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripper.entity.Room;
import com.ripper.response.ControllerResponse;
import com.ripper.service.RoomService;

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
	public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room) {

		Room savedRoom = roomService.addRoom(room);

		return new ResponseEntity<>(savedRoom, HttpStatus.OK);
	}

	@PutMapping("/rooms")
	public ResponseEntity<Room> updateRoom(@Valid @RequestBody Room room, BindingResult bindingResult) {

		Room updatedRoom = roomService.updateRoom(room, bindingResult);
		
		return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
	}

	@DeleteMapping("/rooms")
	public ResponseEntity<ControllerResponse> deleteRoom(@RequestBody Room room) {

		roomService.deleteRoom(room.getRoomName());

		return new ResponseEntity<>(new ControllerResponse(HttpStatus.OK.value(),
				"Room deleted with name " + room.getRoomName(), System.currentTimeMillis()), HttpStatus.OK);
	}

}
