package com.spiderdiary.DAO;

import com.spiderdiary.Entity.Extras.Rozmiar;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.TempForms.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RozmiarRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Spider> findByGenderAndUserAndDlugoscCialaIsNotNullOrderByDlugoscCialaDesc(Gender gender, User user, Sort sort) {
        String jpql = "SELECT s FROM Spider s JOIN s.rozmiar r WHERE s.gender = :gender AND s.user = " +
                ":user AND r.dlugoscCiala IS NOT NULL ORDER BY r.dlugoscCiala DESC";
        TypedQuery<Spider> query = entityManager.createQuery(jpql, Spider.class);
        query.setParameter("gender", gender);
        query.setParameter("user", user);
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.getResultList();
    }

    public List<Spider> findByUserAndDlugoscCialaIsNotNullOrderByDlugoscCialaDesc(User user, Sort sort) {
        String jpql = "SELECT s FROM Spider s JOIN s.rozmiar r WHERE s.user = :user AND r.dlugoscCiala IS NOT NULL ORDER BY r.dlugoscCiala DESC";
        TypedQuery<Spider> query = entityManager.createQuery(jpql, Spider.class);
        query.setParameter("user", user);
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.getResultList();
    }

    public Rozmiar save(Rozmiar rozmiar) {
        if (rozmiar.getId() == null) {
            entityManager.persist(rozmiar);
            return rozmiar;
        } else {
            return entityManager.merge(rozmiar);
        }
    }
}