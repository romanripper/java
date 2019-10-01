package ripper.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ripper.model.Exhibit;
import ripper.model.Exhibition;
import ripper.model.IndividualExhibition;
import ripper.model.Person;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

	/** Returns available artist's exhibits that were exhibited before
	 * 
	 * @param artist of exhibition
	 * @param openDate of exhibition
	 * @param closeDate of exhibition
	 * @return list of exhibits
	 */
	@Query("SELECT exhibit FROM Exhibit exhibit " 
			+ "JOIN exhibit.exhibitions e "
			+ "WHERE "
			+ "exhibit.artist = :artist "
			+ "AND "
			+ "e.openDate >= :closeDate OR e.closeDate <= :openDate")
	List<Exhibit> getAvailableExhibitsForIndividualExhibition(@Param("artist") Person artist,
															  @Param("openDate") LocalDate openDate,
															  @Param("closeDate") LocalDate closeDate);
	
	
	/** Returns available artist's exhibits that were NOT exhibited before
	 * 
	 * @param artist of exhibition
	 * @return list of exhibits
	 */
	@Query("SELECT e FROM Exhibit e " 
			+ "WHERE "
			+ "e.exhibitions IS EMPTY AND e.artist = :artist ")
	List<Exhibit> getAvailableExhibitsForIndividualExhibition(@Param("artist") Person artist);
	
	
	
	/** Returns exhibits of exhibition
	 * 	  
	 * @param artist of exhibition
	 * @return list of exhibits
	 */
	@Query("SELECT e FROM Exhibit e " 
			+ "WHERE "
			+ ":exhibition MEMBER OF e.exhibitions")
	List<Exhibit> getExhibitsOfExhibition(@Param("exhibition") Exhibition exhibition);
	
	/** Returns available exhibits that were exhibited before
	 * 
	 * @param openDate of exhibition
	 * @param closeDate of exhibition
	 * @return list of exhibits
	 */
	@Query("SELECT exhibit FROM Exhibit exhibit " 
			+ "JOIN exhibit.exhibitions e "
			+ "WHERE "
			+ "e.openDate >= :closeDate OR e.closeDate <= :openDate")
	List<Exhibit> getAvailableExhibitsForThemedExhibition(@Param("openDate") LocalDate openDate,
															  @Param("closeDate") LocalDate closeDate);
	
	/** Returns available exhibits that were NOT exhibited before
	 * 
	 * @param artist of exhibition
	 * @return list of exhibits
	 */
	@Query("SELECT e FROM Exhibit e " 
			+ "WHERE "
			+ "e.exhibitions IS EMPTY")
	List<Exhibit> getAvailableExhibitsForThemedExhibition();
	

}
