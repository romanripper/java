package ripper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ripper.model.Exhibit;
import ripper.model.Exhibition;
import ripper.model.IndividualExhibition;
import ripper.model.Person;
import ripper.model.ThemedExhibition;
import ripper.repository.ExhibitRepository;

@Service
public class ExhibitServiceImpl implements ExhibitService {

	@Autowired
	private ExhibitRepository exhibitRepository;
	
	@Override
	@Transactional
	public List<Exhibit> getAllExhibits() {
		return exhibitRepository.findAll();
	}

	@Override
	@Transactional
	public void saveExhibit(Exhibit exhibit) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional
	public Exhibit getExhibitById(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional
	public void deteleExhibitById(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional
	public List<Exhibit> getAvailableExhibitsForIndividualExhibition(IndividualExhibition exhibition) {
		List<Exhibit> availableExhibits = exhibitRepository.getAvailableExhibitsForIndividualExhibition(exhibition.getArtist());
		availableExhibits.addAll(exhibitRepository.getAvailableExhibitsForIndividualExhibition(exhibition.getArtist(), exhibition.getOpenDate(), exhibition.getCloseDate()));
		return availableExhibits;
	}

	@Override
	@Transactional
	public List<Exhibit> getExhibitsByIds(List<Long> ids) {
		return exhibitRepository.findAllById(ids);
	}

	@Override
	@Transactional
	public List<Exhibit> getExhibitsOfExhibition(Exhibition exhibition) {
		return exhibitRepository.getExhibitsOfExhibition(exhibition);
	}

	@Override
	public List<Exhibit> getAvailableExhibitsForThemedExhibition(ThemedExhibition exhibition) {
		List<Exhibit> availableExhibits = exhibitRepository.getAvailableExhibitsForThemedExhibition(exhibition.getOpenDate(), exhibition.getCloseDate());
		availableExhibits.addAll(exhibitRepository.getAvailableExhibitsForThemedExhibition());
		return availableExhibits;
	}

}
