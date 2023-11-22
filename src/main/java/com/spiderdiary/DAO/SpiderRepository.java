package com.spiderdiary.DAO;

import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpiderRepository {

    private EntityManager entityManager;

    @Autowired
    public SpiderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public Spider save(Spider spider) {
        entityManager.merge(spider);
        return spider;
    }

    public List<Spider> findByUser(User user) {
        TypedQuery<Spider> query = entityManager.createQuery(
                "SELECT s FROM Spider s WHERE s.user = :user", Spider.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Spider spider = entityManager.find(Spider.class, id);
        if (spider == null) {
            throw new RuntimeException("No record found for given id: " + id);
        }
        entityManager.remove(spider);
    }

}
