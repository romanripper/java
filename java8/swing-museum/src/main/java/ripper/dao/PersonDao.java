package ripper.dao;

import java.util.List;

public interface PersonDao <Long,Person> {

	void create(Person entity);
    void delete(Person entity);
    void update(Person entity);
    Person findById(Long key);
    List<Person> findAll();
}

