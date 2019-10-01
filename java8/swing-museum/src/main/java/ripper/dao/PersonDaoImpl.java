package ripper.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ripper.model.Person;
import ripper.model.Role;

public class PersonDaoImpl implements PersonDao<Long, Person>{
	private EntityManagerFactory factory;
	private EntityManager manager;

	public PersonDaoImpl() {
		this.manager = getEntityManager();

	}
	public EntityManager getEntityManager() {
		factory = Persistence.createEntityManagerFactory("my_pu");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	@Override
	public void create(Person entity) {
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
	
	public List<Person> getPersonsByRole(Role role) {
		List<Person> persons = null;
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Query query = manager.createQuery("select p from Person p where :role MEMBER OF p.roles");
			persons = query.setParameter("role", role).getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return persons;
	}

	@Override
	public void delete(Person entity) {
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
	public void update(Person entity) {
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
	public Person findById(Long key) {
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Person person = manager.find(Person.class, key);

			transaction.commit();
			return person;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

	@Override
	public List<Person> findAll() {
		List<Person> persons = new ArrayList<>();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			persons = manager.createQuery("Select p from Person p").getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return persons;

	}
	
	public void closeConnection() {
		if(factory != null) {
			manager.close();
			factory.close();
		}
	}
	
	

}
