package com.ripper.validation;

public class ValidationConstants {
	
	public static final String NOT_NULL_MESSAGE = "is required";
	public static final String SIZE_MESSAGE = "must be between {min} and {max} characters long";
	public static final String MIN_MESSAGE = "minimum value is {value}";
	public static final String MAX_MESSAGE = "maximum value is {value}";
	public static final String FUTURE_MESSAGE = "must be in future"; 
	public static final String CONSISTENT_DATE_TIMES_MESSAGE = "dateFrom must be before dateTo";
	
	public static final int SIZE_MIN_FIRST_NAME = 1;
	public static final int SIZE_MAX_FIRST_NAME = 50;

	public static final int SIZE_MIN_LAST_NAME = 1;
	public static final int SIZE_MAX_LAST_NAME = 100;
	
	public static final int SIZE_MIN_LOGIN = 1;
	public static final int SIZE_MAX_LOGIN = 100;
	
	public static final int SIZE_MIN_PASSWORD = 1;
	public static final int SIZE_MAX_PASSWORD = 100;
	
	public static final int SIZE_MIN_ROOM_NAME = 1;
	public static final int SIZE_MAX_ROOM_NAME = 50;
	
	public static final int MIN_NUMBER_OF_SEATS = 5;
	public static final int MAX_NUMBER_OF_SEATS = 100;
	
	public static final int SIZE_MIN_LOCATION_DECRIPTION = 1;
	public static final int SIZE_MAX_LOCATION_DECRIPTION = 256;
	
	public static final String PATTERN_MESSAGE_PHONE_NUMBER = "invalid format. Has to be in format 'dd-dd-dd-dd'";
}
