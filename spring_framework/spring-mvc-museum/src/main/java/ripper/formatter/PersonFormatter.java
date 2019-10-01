package ripper.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ripper.model.Person;
import ripper.service.PersonService;

@Component("personFormatter")
public class PersonFormatter implements Formatter<Person>{
	
	@Autowired
	private PersonService personService;
	
	public PersonFormatter() {
		
	}
	
	@Override
	public String print(Person person, Locale locale) {
		return person.toString();
	}

	@Override
	public Person parse(String personId, Locale locale) throws ParseException {	
		return personService.getPersonById(Long.parseLong(personId));
	}
	
	

}
