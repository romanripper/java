package com.ripper.entity;

import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "is required")
	@Size(max = 50, min = 1, message = "maximum length is 50 characters")
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@NotNull(message = "is required")
	@Size(max = 100, min = 1, message = "maximum length is 50 characters")
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@NotNull(message = "is required")
	@Size(max = 100, min = 1, message = "maximum length is 100 characters")
	@Column(name = "login", nullable = false, unique = true, length = 100)
	private String login;

	@NotNull(message = "is required")
	@Size(max = 100, min = 6, message = "maximum length is 100 characters and minimum 6 characters")
	// BCrypt algorithm generates a String of length 60
	@Column(name = "password", nullable = false, length = 60)
	private String password;

	// Roles of user. With roles we secure every request from unauthorized access.
	// That is why I chose this approach but not ADMIN check for create/edit/delete only.
	// It gives us more flexibility. For example we could easily disallow users with some
	// role to access some resources or enable create/edit/delete for some other roles.
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = null;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
		// we trim string to delete trailing and leading whitespaces
		this.firstName = firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		// we trim string to delete trailing and leading whitespaces
		this.lastName = lastName.trim();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		// we trim string to delete trailing and leading whitespaces
		this.login = login.trim();
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		if(roles == null)
			return roles;
		return new HashSet<>(roles);
	}

	public void setRoles(Set<Role> roles) {
		System.out.println("here");
		this.roles = roles;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		// we trim string to delete trailing and leading whitespaces
		this.password = password.trim();
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
		if(roles == null) {
			roles = new HashSet<>();
		}
		if(roles.isEmpty()) {
			roles.add(Role.ROLE_EMPLOYEE);
		}
	}
}
