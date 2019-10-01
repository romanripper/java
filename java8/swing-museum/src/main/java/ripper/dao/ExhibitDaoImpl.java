package ripper.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ripper.model.Exhibit;
import ripper.model.Person;

public class ExhibitDaoImpl implements ExhibitDao {

	private EntityManagerFactory factory;
	private EntityManager manager;

	public ExhibitDaoImpl() {
		this.manager = getEntityManager();
	}
	
	public EntityManager getEntityManager() {
		factory = Persistence.createEntityManagerFactory("my_pu");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	@Override
	public List<Exhibit> getFreeExhibits(Person artist) {
		List<Exhibit> exhibits = null;
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Query query = manager.createQuery("select e from Exhibit e where e.artist = :artist and e.exhibition = null");
			exhibits = query.setParameter("artist", artist).getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return exhibits;
	}
	
	@Override
	public void update(Set<Exhibit> exhibits) {
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			
			for (Exhibit exhibit : exhibits) {
				manager.merge(exhibit);
			}
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}		
		
	}
	
	public void closeConnection() {
		if(factory != null) {
			manager.close();
			factory.close();
		}
	}

}
