package ripper.service;

import java.util.List;

import ripper.model.Person;
import ripper.model.Role;

public interface PersonService {
	List<Person> getAllPersons();
	void savePerson(Person person);
	Person getPersonById(long id);
	void detelePersonById(long id);
	List<Person> getPersonsByRole(Role role);
}
