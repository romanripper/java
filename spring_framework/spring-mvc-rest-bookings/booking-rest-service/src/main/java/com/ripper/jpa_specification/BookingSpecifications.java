package com.ripper.jpa_specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.ripper.entity.Booking;

public class BookingSpecifications{

	public static Specification<Booking> betweenDates(LocalDateTime dateFrom, LocalDateTime dateTo){
		List<Predicate> predicates = new ArrayList<>();

		return (root, query, criteriaBuilder) -> {
			
			if (dateFrom != null) {
				predicates.add(criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get("dateFrom"), dateFrom),
						criteriaBuilder.greaterThanOrEqualTo(root.get("dateTo"), dateFrom)));
			}
			if (dateTo != null) {
				predicates.add(criteriaBuilder.or(criteriaBuilder.lessThanOrEqualTo(root.get("dateTo"), dateTo),
						criteriaBuilder.lessThanOrEqualTo(root.get("dateFrom"), dateTo)));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
		};	
	}
	
	public static Specification<Booking> reservedByUser(String login) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("user").get("login"), login);
		};	
	}
	
	public static Specification<Booking> reservedRoom(String roomName) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("room").get("roomName"), roomName);
		};	
	}

}
