package com.spiderdiary.DAO;

import com.spiderdiary.Entity.Spider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpiderRepository {

    private EntityManager entityManager;

    @Autowired
    public SpiderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Spider> findByUser_UserName(String username) {
        TypedQuery<Spider> query = entityManager.createQuery("from Spider where user.userName=:uName", Spider.class);
        query.setParameter("uName", username);
        return query.getResultList();
    }

    @Transactional
    public void save(Spider spider) {
        entityManager.merge(spider);
    }
}
