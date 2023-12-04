package com.spiderdiary.Services;

import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.TempForms.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderService {


    private EntityManager entityManager;

    private final SpiderRepository spiderRepository;

    @Autowired
    public SpiderService(SpiderRepository spiderRepository) {
        this.spiderRepository = spiderRepository;
    }

    public Spider save(Spider spider) {
        return spiderRepository.save(spider);
    }

    public List<Spider> searchSpidersForUser(String query, User user) {
        return spiderRepository.searchSpidersForUser(query, user);
    }

    public List<Spider> searchSpidersByGenderAndUser(Gender gender, User user) {
        return spiderRepository.findByGenderAndUser(gender, user);
    }
}
