package com.ripper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ripper.entity.User;
import com.ripper.exception.BadRequestException;
import com.ripper.exception.DataExistsException;
import com.ripper.exception.NotFoundException;
import com.ripper.repository.UserRepository;
import com.ripper.validation.ValidationHandler;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User addUser(User user) {

		if (loginExists(user.getLogin())) {
			throw new DataExistsException("User with such login already exists in the system");
		}

		// in case id in JSON is provided we set id to 0
		// this will force hibernate to create new user instead of update
		user.setId(0);

		
		// encoding password with BCrypt algorithm
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepository.save(user);

		return savedUser;
	}

	@Override
	@Transactional
	public User updateUser(User user, BindingResult bindingResult) {

		// we have to exclude NotNull validation error because properties
		// could be null in update call
		ValidationHandler.validateWithoutNotNullErrorCode(bindingResult);

		if (user.getLogin() == null) {
			throw new BadRequestException("Login is not provided or not valid");
		}

		User userToUpdate = userRepository.findByLogin(user.getLogin());

		// there is no user with such login
		if (userToUpdate == null) {
			throw new NotFoundException("User not found with login " + user.getLogin());
		}

		if (user.getFirstName() != null) {
			userToUpdate.setFirstName(user.getFirstName());
		}

		if (user.getRoles() != null && !(user.getRoles().isEmpty())) {
			userToUpdate.setRoles(user.getRoles());
		}

		if (user.getLastName() != null) {
			userToUpdate.setLastName(user.getLastName());
		}

		if (user.getPassword() != null) {
			// encoding password with BCrypt algorithm
			userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		User updatedUser = userRepository.save(userToUpdate);

		return updatedUser;
	}

	@Override
	@Transactional
	public void deleteUser(String login) {

		if (login == null) {
			throw new BadRequestException("Login is not provided or not valid");
		}
		if (!loginExists(login)) {
			throw new NotFoundException("User not found with login " + login);
		}
		userRepository.deleteByLogin(login);
	}

	@Override
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public boolean loginExists(String login) {
		boolean result = false;
		if (userRepository.findByLogin(login) != null)
			result = true;
		return result;

	}
}
