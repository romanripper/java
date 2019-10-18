package com.ripper.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripper.dao.UserDAO;
import com.ripper.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Override
	@Transactional
	public void deleteUser(String login) {
		userDAO.deleteUser(login);
	}

	@Override
	@Transactional
	public User getUserByLogin(String login) {
		return userDAO.getUserByLogin(login);
	}

}
