package ripper.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ripper.model.ExhibitionRoom;

public class ExhibitionRoomDaoImpl implements ExhibitionRoomDao<Long, ExhibitionRoom> {

	private EntityManager manager;

	public ExhibitionRoomDaoImpl() {
		this.manager = getEntityManager();

	}
	public EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("my_pu");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	@Override
	public void create(ExhibitionRoom entity) {
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
	public void delete(ExhibitionRoom entity) {
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
	public void update(ExhibitionRoom entity) {
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
	public ExhibitionRoom findById(Long key) {
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			ExhibitionRoom exhibitionRoom = manager.find(ExhibitionRoom.class, key);

			transaction.commit();
			return exhibitionRoom;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return null;	}

	@Override
	public List<ExhibitionRoom> findAll() {
		List<ExhibitionRoom> exhibitionRooms = new ArrayList<>();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			exhibitionRooms = manager.createQuery("Select exhibitionR from ExhibitionRoom exhibitionR").getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return exhibitionRooms;
	}

}
