package ripper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ripper.model.Person;
import ripper.model.Role;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	@Query("select p from Person p where :role MEMBER OF p.roles")
	public List<Person> getPersonsByRole(@Param("role") Role role);
	
}
