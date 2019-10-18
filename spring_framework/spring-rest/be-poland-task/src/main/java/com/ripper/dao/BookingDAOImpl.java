package com.ripper.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ripper.entity.Booking;

@Repository
public class BookingDAOImpl implements BookingDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveBooking(Booking booking) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(booking);

	}

	@Override
	public List<Booking> getBookingsForAllRooms(LocalDateTime dateFrom, LocalDateTime dateTo) {
		Session session = sessionFactory.getCurrentSession();

		return buildQuery(session, dateFrom, dateTo, null, null).getResultList();
	}

	@Override
	public List<Booking> getBookingsForRoom(String roomName, LocalDateTime dateFrom, LocalDateTime dateTo) {
		Session session = sessionFactory.getCurrentSession();

		return buildQuery(session, dateFrom, dateTo, null, roomName).getResultList();
	}

	@Override
	public List<Booking> getBookingsForUser(String login, LocalDateTime dateFrom, LocalDateTime dateTo) {
		Session session = sessionFactory.getCurrentSession();

		return buildQuery(session, dateFrom, dateTo, login, null).getResultList();
	}

	
	// builds query for the request
	private Query<Booking> buildQuery(Session session, LocalDateTime dateFrom, LocalDateTime dateTo, String login,
			String roomName) {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
		Root<Booking> booking = cq.from(Booking.class);

		List<Predicate> predicates = new ArrayList<>();

		if (dateFrom != null) {
			predicates.add(cb.or(
					cb.greaterThanOrEqualTo(booking.get("dateFrom"), dateFrom),
					cb.greaterThanOrEqualTo(booking.get("dateTo"), dateFrom)));
		}
		if (dateTo != null) {
			predicates.add(cb.or(
					cb.lessThanOrEqualTo(booking.get("dateTo"), dateTo),
					cb.lessThanOrEqualTo(booking.get("dateFrom"), dateTo)));
		}
		if (login != null) {
			predicates.add(cb.equal(booking.get("user").get("login"), login));
		}
		if (roomName != null) {
			predicates.add(cb.equal(booking.get("room").get("roomName"), roomName));
		}

		cq.select(booking).where(predicates.toArray(new Predicate[] {}));

		return session.createQuery(cq);
	}


}
