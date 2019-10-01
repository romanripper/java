package ripper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ripper.model.Exhibition;
import ripper.repository.ExhibitionRepository;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

	@Autowired
	private ExhibitionRepository exhibitionRepository;

	@Override
	@Transactional
	public List<Exhibition> getAllExhibitions() {
		return exhibitionRepository.findAll();
	}

	@Override
	@Transactional
	public void saveExhibition(Exhibition exhibition) {
		exhibitionRepository.save(exhibition);
	}

	@Override
	@Transactional
	public Exhibition getExhibitionById(long id) {
		return exhibitionRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void deteleExhibitionById(long id) {
		exhibitionRepository.deleteById(id);
	}

}
