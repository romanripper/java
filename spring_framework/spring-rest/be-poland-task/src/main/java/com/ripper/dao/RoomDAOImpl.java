package com.ripper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ripper.entity.Room;

@Repository
public class RoomDAOImpl implements RoomDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Room> getAllRooms() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Room> query = session.createQuery("from Room", Room.class);
		
		return query.getResultList();
	}

	@Override
	public void saveRoom(Room room) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(room);
	}

	@Override
	public void deleteRoom(String roomName) {
		Session session = sessionFactory.getCurrentSession();

		Room roomToDelete = getRoomByName(roomName);
		
		session.delete(roomToDelete);

	}

	@Override
	public Room getRoomByName(String roomName) {
		Session session = sessionFactory.getCurrentSession();

		Query<Room> query = session.createQuery("from Room where roomName=:roomName", Room.class);
		query.setParameter("roomName", roomName);

		List<Room> queryResult = query.getResultList();
		
		// need to check if there exists room with such name
		if(!queryResult.isEmpty())
			return queryResult.get(0);
		else
			return null;
	}

}
