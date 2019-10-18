package com.ripper.dao;

import java.util.List;

import com.ripper.entity.User;

public interface UserDAO {

	List<User> getAllUsers();

	void saveUser(User user);

	void deleteUser(String login);
	
	User getUserByLogin(String login);

}
