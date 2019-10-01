package ripper.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ripper.model.Exhibition;

public class ExhibitionDaoImpl implements ExhibitionDao<Long, Exhibition> {
	private EntityManagerFactory factory;
	private EntityManager manager;

	public ExhibitionDaoImpl() {
		this.manager = getEntityManager();
	}
	
	public EntityManager getEntityManager() {
		factory = Persistence.createEntityManagerFactory("my_pu");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	@Override
	public void create(Exhibition entity) {
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}		
	}

	@Override
	public void delete(Exhibition entity) {
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}		
	}

	@Override
	public void update(Exhibition entity) {
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.merge(entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}		
	}

	@Override
	public Exhibition findById(Long key) {
		EntityTransaction transaction = manager.getTransaction();
		Exhibition exhibition = null;
		try {
			transaction.begin();
			exhibition = manager.find(Exhibition.class, key );

			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return exhibition;

	}

	@Override
	public List<Exhibition> findAll() {
		List<Exhibition> exhibitions = new ArrayList<>();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			exhibitions = manager.createQuery("Select exhibition from Exhibition exhibition").getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return exhibitions;
	}
	
	public void closeConnection() {
		if(factory != null) {
			manager.close();
			factory.close();
		}
	}

}
