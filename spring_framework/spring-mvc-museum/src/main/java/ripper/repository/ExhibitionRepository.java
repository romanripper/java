package ripper.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ripper.model.Exhibition;
import ripper.model.ExhibitionRoom;
import ripper.model.Person;
import ripper.model.Role;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>{
	
}
