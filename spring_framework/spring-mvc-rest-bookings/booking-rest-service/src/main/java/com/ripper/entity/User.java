package com.ripper.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ripper.utils.JPA_Constants;
import com.ripper.validation.ValidationConstants;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_FIRST_NAME, min = ValidationConstants.SIZE_MIN_FIRST_NAME, message = ValidationConstants.SIZE_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_FIRST_NAME, nullable = false, length = JPA_Constants.COLUMN_LENGTH_FIRST_NAME)
	private String firstName;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_LAST_NAME, min = ValidationConstants.SIZE_MIN_LAST_NAME, message = ValidationConstants.SIZE_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_LAST_NAME, nullable = false, length = JPA_Constants.COLUMN_LENGTH_LAST_NAME)
	private String lastName;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_LOGIN, min = ValidationConstants.SIZE_MIN_LOGIN, message = ValidationConstants.SIZE_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_LOGIN, nullable = false, unique = true, length = JPA_Constants.COLUMN_LENGTH_LOGIN)
	private String login;

	@NotNull(message = ValidationConstants.NOT_NULL_MESSAGE)
	@Size(max = ValidationConstants.SIZE_MAX_PASSWORD, min = ValidationConstants.SIZE_MIN_PASSWORD, message = ValidationConstants.SIZE_MESSAGE)
	@Column(name = JPA_Constants.COLUMN_NAME_PASSWORD, nullable = false, length = JPA_Constants.COLUMN_LENGTH_PASSWORD)
	private String password;


	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = JPA_Constants.COLLECTION_TABLE_NAME_ROLES, joinColumns = @JoinColumn(name = JPA_Constants.JOIN_COLUMN_NAME_USER))
	@Column(name = JPA_Constants.COLUMN_NAME_ROLE, nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = null;

	@Column(name = JPA_Constants.COLUMN_NAME_ENABLED, nullable = false)
	private Boolean enabled;

	@JsonIgnore
	@OneToMany(mappedBy = JPA_Constants.MAPPED_BY_USER, cascade = CascadeType.ALL)
	private Set<Booking> bookings;

	public User() {

	}

	public User(String firstName, String lastName, String login, String password, Role role, Boolean enabled) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.roles = new HashSet<>();
		roles.add(role);
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		if (roles == null)
			return roles;
		return new HashSet<>(roles);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Booking> getBookings() {
		return new HashSet<>(bookings);
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	// if boolean not set then default value is false
	@PrePersist
	public void prepareAttributes() {
		if (enabled == null) {
			setEnabled(true);
		}
		if (roles == null) {
			roles = new HashSet<>();
		}
		if (roles.isEmpty()) {
			roles.add(Role.ROLE_EMPLOYEE);
		}
	}
}
