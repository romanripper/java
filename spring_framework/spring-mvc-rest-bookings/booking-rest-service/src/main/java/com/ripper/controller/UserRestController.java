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

import com.ripper.entity.User;
import com.ripper.response.ControllerResponse;
import com.ripper.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User savedUser = userService.addUser(user);

		return new ResponseEntity<>(savedUser, HttpStatus.OK);

	}

	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		User updatedUser = userService.updateUser(user, bindingResult);

		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping("/users")
	public ResponseEntity<ControllerResponse> deleteUser(@RequestBody User user) {
		userService.deleteUser(user.getLogin());

		return new ResponseEntity<>(new ControllerResponse(HttpStatus.OK.value(),
				"User deleted with login " + user.getLogin(), System.currentTimeMillis()), HttpStatus.OK);
	}
}
