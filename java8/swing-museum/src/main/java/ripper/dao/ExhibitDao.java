package ripper.dao;

import java.util.List;
import java.util.Set;

import ripper.model.Exhibit;
import ripper.model.Person;

public interface ExhibitDao {
	List<Exhibit> getFreeExhibits(Person artist);
	void update(Set<Exhibit> exhibits);
}
