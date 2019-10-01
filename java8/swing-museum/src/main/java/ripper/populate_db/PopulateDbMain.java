package ripper.populate_db;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ripper.model.Curator;
import ripper.model.Exhibit;
import ripper.model.Exhibition;
import ripper.model.ExhibitionLengthType;
import ripper.model.ExhibitionRoom;
import ripper.model.IndividualExhibition;
import ripper.model.Permanent;
import ripper.model.Person;
import ripper.model.Role;
import ripper.model.Temporary;
import ripper.model.ThemedExhibition;

public class PopulateDbMain {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_pu");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {

		transaction.begin();
			
		Person artist1 = new Person("Leonardo", "da Vinci","KL932304", Role.ARTIST, null);
		Person artist2_curator1 = new Person("Vincent", "van Gogh","FB912364", Role.ARTIST, "one_eared");
		artist2_curator1.addRole(Role.CURATOR, 2000.0);
		
		Person artist3 = new Person("Edvard", "Munch","AM637564", Role.ARTIST, "Scream man");
		Person artist4 = new Person("Thomas", "Edison","KH934359", Role.ARTIST, null);

		Exhibit exhibit1_1 = new Exhibit("The Last Supper",
				"Last meal shared by Jesus with his disciples before his capture and death",
				artist1);
		Exhibit exhibit1_2 = new Exhibit("Mona Lisa",
				"In the painting Virgin and Child with St. Anne, the composition again picks up the theme of figures"
						+ " in a landscape",
				artist1);
		Exhibit exhibit1_3 = new Exhibit("Vitruvian Man",
				"This image demonstrates the blend of mathematics and art during the Renaissance and demonstrates"
						+ " Leonardo's deep understanding of proportion",
				artist1);
		Exhibit exhibit1_4 = new Exhibit("Lady with an Ermine", "The portrait of Cecilia Gallerani (c. 1483-1490)",
				artist1);
		Exhibit exhibit1_5 = new Exhibit("A design for a flying machine",
				"Leonardo's design for a flying machine (1488)", artist1);

		Exhibit exhibit2_1 = new Exhibit("Sunflowers", "Fourth version, 1880", artist2_curator1);
		Exhibit exhibit2_2 = new Exhibit("Self-portrait", "Self-portrait with Bandaged Ear and Pipe, 1889", artist2_curator1);
		Exhibit exhibit2_3 = new Exhibit("The Starry Night", "June 1889", artist2_curator1);

		Exhibit exhibit3_1 = new Exhibit("The Scream",
				"It has been widely interpreted as representing the universal anxiety of modern man, 1893", artist3);

		Exhibit exhibit4_1 = new Exhibit("Phonograph",
				"The invention that first gained Thomas Edison wide notice, 1877", artist4);
		Exhibit exhibit4_2 = new Exhibit("Incandescent light bulb",
				"Original carbon-filament bulb from Thomas Edison's shop in Menlo Park", artist4);
		Exhibit exhibit4_3 = new Exhibit("Fluoroscopy",
				"Edison discovered that calcium tungstate fluoroscopy screens produced brighter images"
						+ " than the barium platinocyanide screens originally used by Wilhelm Rontgen",
				artist4);

		Exhibit exhibit1 = new Exhibit("Tutankhamun`s Mask",
				"Discovered by Howard Carter in 1925, it is the death mask of the Egyptian Pharaoh of "
						+ "18th dynasty Tutankhamun who reigned 1332�1323 BC");
		Exhibit exhibit2 = new Exhibit("The Grave Mask of King Amenemope",
				"Amenemope was an ancient Egyptian Pharaoh of the 21st dynasty, who was also"
						+ " the son and the successor of Psusennes I. The Grave Mask is made of gold and cartonnage.");
		Exhibit exhibit3 = new Exhibit("The Pallette of Narmer",
				"The palette dates back to the 31st century BC, and holds some of the earliest hieroglyphic"
						+ " engravings that have ever been found. It is believed that the palette portrays King Narmer�s unification of the Upper and Lower Egypt.");
		Exhibit exhibit4 = new Exhibit("Statue of Khufu",
				"The statue of Khufu, also known as the Ivory figurine of Khufu, was discovered by Flinders Pitrie in"
						+ " 1903 during an excavation in Abydos, Egypt.");
		Exhibit exhibit5 = new Exhibit("Statue of Khafra",
				"Khafra, Khefren, or Chephren, was an ancient Egpytian pharoah of the 4th dynasty, son of Khufu,"
						+ " and the builder of pyramid of Khafra at Giza. Some authors say that the Great Sphinx of Giza"
						+ "");
		Exhibit exhibit6 = new Exhibit("Merneptah Stele", "Also known as the Victory Stele of Merneptah or Israel Stele");
		
		ExhibitionRoom exhibitionRoom1 = new ExhibitionRoom(12, 160);
		ExhibitionRoom exhibitionRoom2 = new ExhibitionRoom(16, 150);
		ExhibitionRoom exhibitionRoom3 = new ExhibitionRoom(11, 50);
		ExhibitionRoom exhibitionRoom4 = new ExhibitionRoom(10, 200);
		ExhibitionRoom exhibitionRoom5 = new ExhibitionRoom(18, 100);
		ExhibitionRoom exhibitionRoom6 = new ExhibitionRoom(2, 70);
		ExhibitionRoom exhibitionRoom7 = new ExhibitionRoom(5, 120);
		ExhibitionRoom exhibitionRoom8 = new ExhibitionRoom(1, 200);
		ExhibitionRoom exhibitionRoom9 = new ExhibitionRoom(3, 95);
		ExhibitionRoom exhibitionRoom10 = new ExhibitionRoom(8, 130);
		
		Person curator2 = new Person("Vito", "Corleone","BO936380", 8000.0, Role.CURATOR);
		Person curator3 = new Person("Mike", "Corleone","RH236340", 7000.0, Role.CURATOR);
		Person curator4 = new Person("Sonny", "Corleone","KW235360", 7000.0, Role.CURATOR);
		Person curator5 = new Person("Tom", "Hagen","RM032330", 7000.0, Role.CURATOR);
		Person curator6 = new Person("Genco", " Abbandando","PO336590", 5000.0, Role.CURATOR);
		Person curator7 = new Person("Luca", "Brasi","GA923680", 5000.0, Role.CURATOR);
		Person curator8 = new Person("Peter", "Clementza","KR735339", 5000.0, Role.CURATOR);

		Set<String> keywords1 = new HashSet<>();
		keywords1.add("Egypt");
		keywords1.add("Khafra");
		keywords1.add("Merneptah Stele");
		keywords1.add("Tutankhamun");
		keywords1.add("Great Sphinx");
		Exhibition exhibition1 = new ThemedExhibition("Fascinated Ancient Egypt", LocalDate.of(2019, 6, 10), LocalDate.of(2025, 6, 10), 5.0, "Ancient Egypt", curator2, keywords1);
		
		Set<String> keywords2 = new HashSet<>();
		keywords2.add("Thomas Edison");
		keywords2.add("Phonograph");
		keywords2.add("Light bulb");
		keywords2.add("Menlo Park");
		Exhibition exhibition2 = new IndividualExhibition("Famous inventors: Thomas Edison", LocalDate.of(2019, 2, 14), LocalDate.of(2020, 8, 17), 20.0, artist4, curator3, keywords2);
		
		Set<String> keywords3 = new HashSet<>();
		keywords3.add("Sunflowers");
		keywords3.add("The Starry Night");
		keywords3.add("van Gogh");
		Exhibition exhibition3 = new IndividualExhibition("Famous painters: Vincent van Gogh", LocalDate.of(2018, 8, 14), LocalDate.of(2020, 8, 30), 20.0, artist2_curator1, curator5, keywords3);
	
		
		exhibition1.addExhibitionRoom(exhibitionRoom1);
		exhibition1.addExhibitionRoom(exhibitionRoom2);
		exhibition1.addExhibitionRoom(exhibitionRoom3);
		exhibition1.addExhibitionRoom(exhibitionRoom4);
		
		exhibition2.addExhibitionRoom(exhibitionRoom5);
		exhibition2.addExhibitionRoom(exhibitionRoom6);
		
		exhibition3.addExhibitionRoom(exhibitionRoom7);

		manager.persist(exhibit1);
		manager.persist(exhibit2);
		manager.persist(exhibit3);
		manager.persist(exhibit4);
		manager.persist(exhibit5);
		manager.persist(exhibit6);
		
		manager.persist(curator2);
		manager.persist(curator3);
		manager.persist(curator5);
		
		manager.persist(curator6);
		manager.persist(curator7);
		manager.persist(curator8);
		
		
		manager.persist(artist1);
		manager.persist(artist3);
		manager.persist(artist4);
		
		manager.persist(exhibit2_1);
		manager.persist(exhibit2_2);
		manager.persist(exhibit2_3);
		
		manager.persist(exhibit4_1);
		manager.persist(exhibit4_2);
		manager.persist(exhibit4_3);
		
		manager.persist(exhibit1_1);
		manager.persist(exhibit1_2);
		manager.persist(exhibit1_3);
		manager.persist(exhibit1_4);
		manager.persist(exhibit1_5);
		
		manager.persist(exhibit3_1);
		
		manager.persist(exhibition1);
		manager.persist(exhibition2);
		manager.persist(exhibition3);
		
		ExhibitionLengthType exhibitionLengthType1 = new Permanent(exhibition1, "http://electronicCatalogue/fascinatingAncientEgypt/");		
		ExhibitionLengthType exhibitionLengthType2 = new Temporary(exhibition2, artist2_curator1);
		ExhibitionLengthType exhibitionLengthType3 = new Temporary(exhibition3, curator4);
		
		manager.persist(exhibitionLengthType1);
		manager.persist(exhibitionLengthType2);
		manager.persist(exhibitionLengthType3);
		
		manager.persist(artist2_curator1);
		manager.persist(curator4);
		
		
		
		manager.persist(exhibitionRoom8);
		manager.persist(exhibitionRoom9);
		manager.persist(exhibitionRoom10);
		
		
		manager.persist(curator2);
		manager.persist(curator3);
		manager.persist(curator5);
		
		manager.persist(curator6);
		manager.persist(curator7);
		manager.persist(curator8);
		
		exhibition1.addExhibit(exhibit1);
		exhibition1.addExhibit(exhibit2);
		exhibition1.addExhibit(exhibit3);
		exhibition1.addExhibit(exhibit4);
		exhibition1.addExhibit(exhibit5);
		exhibition1.addExhibit(exhibit6);
		exhibit1.setExhibitionRoom(exhibitionRoom1);
		exhibit2.setExhibitionRoom(exhibitionRoom2);
		exhibit3.setExhibitionRoom(exhibitionRoom1);
		exhibit4.setExhibitionRoom(exhibitionRoom3);
		exhibit5.setExhibitionRoom(exhibitionRoom4);
		exhibit6.setExhibitionRoom(exhibitionRoom3);
		
		exhibition2.addExhibit(exhibit4_1);
		exhibition2.addExhibit(exhibit4_2);
		exhibition2.addExhibit(exhibit4_3);
		exhibit4_1.setExhibitionRoom(exhibitionRoom5);
		exhibit4_2.setExhibitionRoom(exhibitionRoom5);
		exhibit4_3.setExhibitionRoom(exhibitionRoom6);
		
		exhibition3.addExhibit(exhibit2_1);
		exhibition3.addExhibit(exhibit2_2);
		exhibition3.addExhibit(exhibit2_3);
		exhibit2_1.setExhibitionRoom(exhibitionRoom7);
		exhibit2_2.setExhibitionRoom(exhibitionRoom7);
		exhibit2_3.setExhibitionRoom(exhibitionRoom7);
		
		transaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		
		System.out.println("\n=====>>>End");

	}
}
