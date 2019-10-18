package com.ripper.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripper.entity.User;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.DataExistsException;
import com.ripper.exception.InvalidDataPassedException;
import com.ripper.exception.NotFoundException;
import com.ripper.response.ControllerResponse;
import com.ripper.service.UserService;
import com.ripper.utils.ValidationErrorCode;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {

		// validation of passed data
		if (bindingResult.hasErrors()) {
			System.out.println("\n\n\n" + bindingResult);
			List<String> errors = bindingResult.getFieldErrors().stream()
					.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
					.collect(Collectors.toList());
			if (!errors.isEmpty())
				throw new InvalidDataPassedException(errors);
		}
		
		// there is a user with such login
		if (userService.getUserByLogin(user.getLogin()) != null) {
			throw new DataExistsException("User with such login already exists in the system");
		}

		// in case id in JSON is provided we set id to 0
		// this will force hibernate to create new user instead of update
		user.setId(0);

		// encoding password with BCrypt algorithm
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userService.saveUser(user);

		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {

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

		// provided login is null or not provided at all
		if (user.getLogin() == null) {
			throw new BadRequestException("Login is not provided or not valid");
		}

		User userToUpdate = userService.getUserByLogin(user.getLogin());

		// there is no user with such login
		if (userToUpdate == null) {
			throw new NotFoundException("User not found with login " + user.getLogin());
		}

		// if firstName is provided we have to update it in our userToUpdate
		if (user.getFirstName() != null) {
			userToUpdate.setFirstName(user.getFirstName());
		}

		// if roles are provided we have to update it in our userToUpdate
		if (user.getRoles() != null 
				&& !(user.getRoles().isEmpty())) {
			userToUpdate.setRoles(user.getRoles());
		}

		// if lastName is provided we have to update it in our userToUpdate
		if (user.getLastName() != null) {
			userToUpdate.setLastName(user.getLastName());
		}

		// if password is provided we have to update it in our userToUpdate
		if (user.getPassword() != null) {

			// encoding password with BCrypt algorithm
			userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		userService.saveUser(userToUpdate);

		return new ResponseEntity<>(userToUpdate, HttpStatus.OK);

	}

	@DeleteMapping("/users")
	public ResponseEntity<ControllerResponse> deleteUser(@RequestBody User user) {

		// provided login is null or not provided at all
		if (user.getLogin() == null) {
			throw new BadRequestException("Login is missing or not valid");
		}

		// we don't need to check login if its length <= 50 because system will not let
		// such value to be created in the database

		// there is no user with such login
		if (userService.getUserByLogin(user.getLogin()) == null) {
			throw new NotFoundException("User not found with login " + user.getLogin());
		}

		userService.deleteUser(user.getLogin());

		return new ResponseEntity<>(new ControllerResponse(HttpStatus.OK.value(),
				"User deleted with login " + user.getLogin(), System.currentTimeMillis()), HttpStatus.OK);
	}
}
