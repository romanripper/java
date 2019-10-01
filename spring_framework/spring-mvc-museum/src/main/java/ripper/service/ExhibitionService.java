package ripper.service;

import java.util.List;

import ripper.model.Exhibition;

public interface ExhibitionService {
	List<Exhibition> getAllExhibitions();
	void saveExhibition(Exhibition exhibition);
	Exhibition getExhibitionById(long id);
	void deteleExhibitionById(long id);
}
