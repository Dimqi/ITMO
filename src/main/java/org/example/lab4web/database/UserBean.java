package org.example.lab4web.database;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.lab4web.entity.UserEntity;

import jakarta.ejb.Stateless;
import java.util.Optional;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "Lab3Web")
    private EntityManager em;

    public Optional<UserEntity> findByUsername(String username) {
        try {
            UserEntity u = em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(u);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void save(UserEntity user) {
        em.persist(user);
    }
}