package ripper.service;

import java.util.List;

import ripper.model.Exhibit;
import ripper.model.Exhibition;
import ripper.model.IndividualExhibition;
import ripper.model.Person;
import ripper.model.ThemedExhibition;

public interface ExhibitService {
	List<Exhibit> getAllExhibits();
	void saveExhibit(Exhibit exhibit);
	Exhibit getExhibitById(long id);
	void deteleExhibitById(long id);
	
	List<Exhibit> getAvailableExhibitsForIndividualExhibition(IndividualExhibition exhibition);
	List<Exhibit> getAvailableExhibitsForThemedExhibition(ThemedExhibition exhibition);
	/**
	 * Returns list of exhibits with provides ids
	 */
	List<Exhibit> getExhibitsByIds(List<Long> ids);
	List<Exhibit> getExhibitsOfExhibition(Exhibition exhibition);
	
}
