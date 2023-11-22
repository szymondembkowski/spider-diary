package com.spiderdiary.DAO;

import com.spiderdiary.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    public User findByUserName(String theUserName) {

        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", theUserName);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Transactional
    public void save(User theUser) {

        entityManager.merge(theUser);
    }
}