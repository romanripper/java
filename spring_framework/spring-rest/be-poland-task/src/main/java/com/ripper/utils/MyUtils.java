package com.ripper.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ripper.entity.Booking;
import com.ripper.response_wrapper.BookingResponseWrapper;

public class MyUtils {
	public static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String PHONE_NUMBER_PATTERN = "^(\\d{2}-){3}\\d{2}$";
	
	//prepares error body response based on exception 
	public static Map<String, Object> prepareBody(Exception exc, int errorCode) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("status", errorCode);
		data.put("error", exc.getMessage());
		data.put("timestamp", System.currentTimeMillis());
		return data;
	}
	
	//prepares BookingResponseList based on Bookings
	public static List<BookingResponseWrapper> prepareBookingResposeList(List<Booking> retreivedBookings){
		List<BookingResponseWrapper> result = new ArrayList<>();
		retreivedBookings.stream()
				.forEach(booking -> result.add(
						new BookingResponseWrapper(booking.getRoom().getRoomName(), booking.getUser().getFirstName(),
													booking.getUser().getLastName(), booking.getDateFrom(), booking.getDateTo())));
		return result;
	}
	
}
