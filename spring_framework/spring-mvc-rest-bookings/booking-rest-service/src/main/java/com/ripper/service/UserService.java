package com.ripper.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.ripper.entity.User;

public interface UserService {
	List<User> getAllUsers();

	User addUser(User user);
	
	User updateUser(User user, BindingResult bindingResult);

	void deleteUser(String login);
	
	User getUserByLogin(String login);
	
	boolean loginExists(String login);
}
