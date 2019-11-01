package com.ripper.utils;

public class JPA_Constants {

	// User
	public static final String COLUMN_NAME_FIRST_NAME = "first_name";
	public static final int COLUMN_LENGTH_FIRST_NAME = 50;
	
	public static final String COLUMN_NAME_LAST_NAME = "last_name";
	public static final int COLUMN_LENGTH_LAST_NAME = 100;
	
	public static final String COLUMN_NAME_LOGIN = "login";
	public static final int COLUMN_LENGTH_LOGIN = 100;
	
	public static final String COLUMN_NAME_PASSWORD = "password";
	public static final int COLUMN_LENGTH_PASSWORD = 60;
	
	public static final String COLUMN_NAME_ROLE = "role";
	
	public static final String COLUMN_NAME_ENABLED = "enabled";

	// Room
	public static final String COLUMN_NAME_ROOM_NAME = "room_name";
	public static final int COLUMN_LENGTH_ROOM_NAME = 50;
	
	public static final String COLUMN_NAME_LOCATION_DESCRIPTION = "location_description";
	public static final int COLUMN_LENGTH_LOCATION_DESCRIPTION = 256;
	
	public static final String COLUMN_NAME_NUMBER_OF_SEATS = "number_of_seats";
	
	public static final String COLUMN_NAME_HAS_PROJECTOR = "has_projector";
	
	public static final String COLUMN_NAME_PHONE_NUBMER = "phone_nubmer";
	public static final int COLUMN_LENGTH_PHONE_NUMBER = 20;

	// Booking
	public static final String COLUMN_NAME_DATE_FROM = "date_from";
	
	public static final String COLUMN_NAME_DATE_TO = "date_to";

	// Mappings
	public static final String COLLECTION_TABLE_NAME_ROLES = "users_roles";
	
	public static final String JOIN_COLUMN_NAME_USER = "user_id";
	
	public static final String JOIN_COLUMN_NAME_ROOM = "room_id";

	public static final String MAPPED_BY_USER = "user";
	
	public static final String MAPPED_BY_ROOM = "room";

	

}
