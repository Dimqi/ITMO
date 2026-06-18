package database;

import java.util.ArrayList;

import Entity.HitEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DatabaseManager {

	public void save(HitEntity hitEntity) {
		entityManager.persist(hitEntity);
	}

	@PersistenceContext(unitName = "Lab3Web")
	private EntityManager entityManager;

	

	public ArrayList<HitEntity> getAllResults() {
		return new ArrayList<>(entityManager
				.createQuery("SELECT h FROM HitEntity h ORDER BY h.id DESC", HitEntity.class)
				.getResultList());

	}
}
