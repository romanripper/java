package ripper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ripper.model.Person;
import ripper.model.Role;
import ripper.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	@Transactional
	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}
	
	@Transactional
	public List<Person> getPersonsByRole(Role role){
		return personRepository.getPersonsByRole(role);
	}

	@Override
	@Transactional
	public void savePerson(Person person) {
		personRepository.save(person);
	}

	@Override
	@Transactional
	public Person getPersonById(long id) {
		return personRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void detelePersonById(long id) {
		throw new UnsupportedOperationException();
	}

}
