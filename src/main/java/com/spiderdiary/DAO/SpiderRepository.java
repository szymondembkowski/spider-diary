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

    public Optional<Spider> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Spider.class, id));
    }

    public List<Spider> findAllSortedByMoltDate(User user) {
        TypedQuery<Spider> query = entityManager.createQuery(
                "SELECT s FROM Spider s WHERE s.user = :user ORDER BY s.moltDate DESC", Spider.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Spider> findAllSortedByName(User user) {
        TypedQuery<Spider> query = entityManager.createQuery(
                "SELECT s FROM Spider s WHERE s.user = :user ORDER BY s.name ASC", Spider.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Spider> searchSpidersForUser(String query, User user) {
        String jpqlQuery = "SELECT s FROM Spider s WHERE (s.name LIKE :query OR s.species LIKE :query) AND s.user = :user";
        TypedQuery<Spider> typedQuery = entityManager.createQuery(jpqlQuery, Spider.class);
        typedQuery.setParameter("query", "%" + query + "%");
        typedQuery.setParameter("user", user);
        return typedQuery.getResultList();
    }

}