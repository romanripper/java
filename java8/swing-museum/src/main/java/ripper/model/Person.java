/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ripper.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "person")
public class Person implements Artist, Curator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "passport_number", nullable = false)
	private String passportNumber; 
	
	@Column(name = "salary")
	private Double salary;
	
	@Column(name = "nick")
	private String nick;

	@OneToMany(mappedBy = "artist", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<Exhibit> exhibits = new HashSet<>();

	@OneToMany(mappedBy = "curator",
			cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Set<Exhibition> exhibitions;
	
	@OneToMany(mappedBy = "secondCurator", targetEntity=Temporary.class)
	private Set<Exhibition> temporaryExhibitions;
	
	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	private Set<IndividualExhibition> individualExhibitions = new HashSet<>();

	@ElementCollection(targetClass = Role.class)
	@JoinTable(name = "person_role", joinColumns = @JoinColumn(name = "person_id"))
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = new HashSet<>();

	public Person() {

	}

	public Person(String fisrstName, String lastName,String passportNumber, Double salary, Role curatorRole) {
		addRole(curatorRole, salary);
		setPassportNumber(passportNumber);
		setFirstName(fisrstName);
		setLastName(lastName);
		setExhibits(null);
	}

	public Person(String firstName, String lastName,String passportNumber, Role authorRole, String nick) {
		addRole(authorRole, null);
		setPassportNumber(passportNumber);
		setFirstName(firstName);
		setLastName(lastName);
		setNick(nick);
	}

	public Person(String firstName, String lastName,String passportNumber, Double salary, Set<Role> personRoles) {
		setRoles(personRoles);
		setPassportNumber(passportNumber);
		setFirstName(firstName);
		setLastName(lastName);
		setSalary(salary);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		if (name == null) {
			throw new RuntimeException("Name can not be null");
		}
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String surname) {
		if (surname == null) {
			throw new RuntimeException("Surname can not be null");
		}
		this.lastName = surname;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		if(passportNumber == null) {
			throw new RuntimeException("Passport number can not be null");
		}
		this.passportNumber = passportNumber;
	}

	public Set<Role> getRoles() {
		return new HashSet<>(roles);
	}

	private void setRoles(Set<Role> roles) {
		if (roles == null) {
			throw new RuntimeException("Roles can not be null");
		}
		if (roles.isEmpty()) {
			throw new RuntimeException("Roles can not be empty");
		}
		this.roles = roles;
	}

	public void addRole(Role role, Double salary) {
		if (!roles.contains(role)) {
			if (role == null) {
				throw new RuntimeException("Role can not be null");
			}
			if(role.equals(Role.ARTIST)) {
				setExhibits(new HashSet<>());
				setIndividualExhibitions(new HashSet<>());
				roles.add(role);
			}
			else {
				roles.add(role);
				setSalary(salary);
				setExhibitions(new HashSet<Exhibition>());
				setTemporaryExhibitions(new HashSet<Exhibition>());
			}
			
		}
	}

	public void removeRole(Role role) {
		if (roles.size() == 1) {
			throw new RuntimeException("PersonServiceImpl has to have at least 1 role");
		}
		switch (role) {
		case ARTIST:
			// cleaning attributes of artist
			setNick(null);
			
			Set<Exhibit> tempExhibits = getExhibits();
			for (Exhibit exhibit : tempExhibits) {
				exhibit.setArtist(null);
			}
			setExhibits(null);
			tempExhibits = null;
			
			Set<IndividualExhibition> tempIndividualExhibitions = getIndividualExhibitions();
			for (IndividualExhibition individualExhibition : tempIndividualExhibitions) {
				removeIndividualExhibition(individualExhibition);
			}
			setIndividualExhibitions(null);
			tempIndividualExhibitions = null;
			
			break;

		case CURATOR:
			//cleaning attributes of curator
			setSalary(null);
			
			Set<Exhibition> tempExhibitions = getExhibitions();
			for(Exhibition exhibition: tempExhibitions) {
				removeExhibition(exhibition);
			}
			setExhibitions(null);
			tempExhibitions = null;
			
			Set<Exhibition> tempTemporaryExhibitions = getTemporaryExhibitions();
			for(Exhibition exhibition: tempTemporaryExhibitions) {
				removeTemporaryExhibition(exhibition);
			}
			setTemporaryExhibitions(null);
			tempTemporaryExhibitions = null;
			
			break;
		default:
			break;
		}
		roles.remove(role);
	}

//---------------------------ARTIST-------------------------------

	@Override
	public String getNick() {
		return nick;
	}

	@Override
	public void setNick(String nick) {
		if(roles.contains(Role.ARTIST))
			this.nick = nick;
		else {
			throw new RuntimeException("This person is not artist, he can not have nick");
		}
	}
	
	@Override
	public Set<Exhibit> getExhibits() {
		return new HashSet<Exhibit>(exhibits);
	}

	@Override
	public void setExhibits(Set<Exhibit> exhibits) {
		this.exhibits = exhibits;
	}

	@Override
	public void addExhibit(Exhibit exhibit) {
		if (roles.contains(Role.ARTIST)) {
			if (!exhibits.contains(exhibit)) {
				if (exhibit == null) {
					throw new RuntimeException("Exhibit can not be null");
				}
				exhibits.add(exhibit);
				exhibit.setArtist(this);
			}
		} else {
			throw new RuntimeException("This person is not artist, he can not have exhibits");
		}
	}

	@Override
	public void removeExhibit(Exhibit exhibit) {
		if (roles.contains(Role.ARTIST)) {
			if (exhibits.contains(exhibit)) {
				exhibits.remove(exhibit);
				exhibit.setArtist(null);
			}
		} else {
			throw new RuntimeException("This person is not artist, he can not removes exhibits");
		}
	}
	
	public Set<IndividualExhibition> getIndividualExhibitions() {
		return new HashSet<>(individualExhibitions);
	}

	public void setIndividualExhibitions(Set<IndividualExhibition> individualExhibition) {
		this.individualExhibitions = individualExhibition;
	}
	
	@Override
	public void addIndividualExhibition(IndividualExhibition individualExhibition) {
		if (roles.contains(Role.ARTIST)) {
			if (!individualExhibitions.contains(individualExhibition)) {
				if (individualExhibition == null) {
					throw new RuntimeException("Individual exhibition can not be null");
				}
				if(individualExhibition.getArtist() != this) {
					throw new RuntimeException("Individual exhibition of another artist");
				}
				individualExhibitions.add(individualExhibition);
			}
		} else {
			throw new RuntimeException("This person is not an artist, he can not have individual exhibitions");
		}
		
	}
	
	@Override
	public void removeIndividualExhibition(IndividualExhibition individualExhibition) {
		if (roles.contains(Role.ARTIST)) {
			if (individualExhibitions.contains(individualExhibition)) {
				individualExhibitions.remove(individualExhibition);
				individualExhibition.remove();
			}
		} else {
			throw new RuntimeException("This person is not an artist, he can not removes exhibits");
		}
	}
	

	// ----------------------------ARTIST---------------------------------

	// ---------------------------CURATOR-------------------------------

	@Override
	public Double getSalary() {
		return salary;
	}

	@Override
	public void setSalary(Double salary) {
		if (roles.contains(Role.CURATOR)) {
			if (salary == null) {
				throw new RuntimeException("Salary can not be null");
			}
			if (salary < 0.0) {
				throw new RuntimeException("Salary can not be less than zero");
			}
			this.salary = salary;
		} else {
			if (salary != null) {
				throw new RuntimeException("This person is not curator, he does not have salary");
			}
			this.salary = salary;
		}
	}

	
	@Override
	public Set<Exhibition> getExhibitions() {
		return new HashSet<>(exhibitions);
	}

	@Override
	public void setExhibitions(Set<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}
	
	@Override
	public void addExhibition(Exhibition exhibition) {
		if (roles.contains(Role.CURATOR)) {
			if (!exhibitions.contains(exhibition)) {
				if (exhibition == null) {
					throw new RuntimeException("Exhibition can not be null");
				}
				if(exhibition.getCurator() != this) {
					throw new RuntimeException("Exhibition of another curator");
				}
				exhibitions.add(exhibition);
			}
		} else {
			throw new RuntimeException("This person is not curator, he can not have exhibitions");
		}
		
	}

	@Override
	public void removeExhibition(Exhibition exhibition) {
		if (roles.contains(Role.CURATOR)) {
			if (exhibitions.contains(exhibition)) {
				exhibitions.remove(exhibition);
				exhibition.setCurator(null);
			}
		} else {
			throw new RuntimeException("This person is not curator, he can not remove exhibitions");
		}
		
	}

	@Override
	public Set<Exhibition> getTemporaryExhibitions() {
		return new HashSet<Exhibition>(temporaryExhibitions);
	}

	@Override
	public void setTemporaryExhibitions(Set<Exhibition> temporaryExhibitions) {
		this.temporaryExhibitions = temporaryExhibitions; 
		
	}

	@Override
	public void addTemporaryExhibition(Exhibition temporaryExhibition) {
		if (roles.contains(Role.CURATOR)) {
			if (!temporaryExhibitions.contains(temporaryExhibition)) {
				if (temporaryExhibition == null) {
					throw new RuntimeException("Temporary exhibition can not be null");
				}
				if(temporaryExhibition.getExhibitionLengthType() instanceof Permanent) {
					throw new RuntimeException("Exhibition has to be temporary");
				}
				if(((Temporary)temporaryExhibition.getExhibitionLengthType()).getSecondCurator() != this) {
					throw new RuntimeException("Temporary exhibition of another curator");
				}
				temporaryExhibitions.add(temporaryExhibition);
			}
		} else {
			throw new RuntimeException("This person is not curator, he can not have temporary exhibitions");
		}	
	}

	@Override
	public void removeTemporaryExhibition(Exhibition temporaryExhibition) {
		if (roles.contains(Role.CURATOR)) {
			if (temporaryExhibitions.contains(temporaryExhibition)) {
				temporaryExhibitions.remove(temporaryExhibition);
				((Temporary)temporaryExhibition.getExhibitionLengthType()).setSecondCurator(null);
			}
		} else {
			throw new RuntimeException("This person is not curator, he can not remove temporary exhibitions");
		}
		
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	// ---------------------------CURATOR-------------------------------

	
}
