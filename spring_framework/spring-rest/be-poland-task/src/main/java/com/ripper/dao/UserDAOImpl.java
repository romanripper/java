package com.ripper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ripper.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<User> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();

		Query<User> query = session.createQuery("from User", User.class);

		return query.getResultList();
	}

	@Override
	public void saveUser(User user) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String login) {
		Session session = sessionFactory.getCurrentSession();
		
		User userTodelete = getUserByLogin(login);
		
		session.delete(userTodelete);
	}

	@Override
	public User getUserByLogin(String login) {
		Session session = sessionFactory.getCurrentSession();

		Query<User> query = session.createQuery("from User where login=:login", User.class);
		query.setParameter("login", login);
		
		
		List<User> queryResult = query.getResultList();
		// need to check if there exists user with such login
		if(!queryResult.isEmpty())
			return queryResult.get(0);
		else
			return null;
	}

}
