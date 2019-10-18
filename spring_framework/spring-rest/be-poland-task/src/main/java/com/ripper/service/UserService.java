package com.ripper.service;

import java.util.List;

import com.ripper.entity.User;

public interface UserService {
	List<User> getAllUsers();

	void saveUser(User user);

	void deleteUser(String login);
	
	User getUserByLogin(String login);
}
