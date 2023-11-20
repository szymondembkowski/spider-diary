package com.spiderdiary.Services;

import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.Entity.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderService {

    private SpiderRepository spiderRepository;

    @Autowired
    public SpiderService(SpiderRepository spiderRepository) {
        this.spiderRepository = spiderRepository;
    }

    public List<Spider> getSpidersByUser(String username) {
        // Implementacja pobierania pająków na podstawie nazwy użytkownika
        return spiderRepository.findByUser_UserName(username);
    }
}
