package org.example.lab4web.database;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.lab4web.entity.ResultEntity;
import org.example.lab4web.entity.UserEntity;

import java.util.List;

@Stateless
public class ResultBean {

    @PersistenceContext(unitName= "Lab3Web")
    private EntityManager em;


    public void addResult(ResultEntity result) {
        em.persist(result);
    }

    public List<ResultEntity> findByUser(UserEntity user) {
        return em.createQuery("SELECT r FROM ResultEntity r WHERE r.user = :user", ResultEntity.class)
                .setParameter("user", user)
                .getResultList();
    }

}
