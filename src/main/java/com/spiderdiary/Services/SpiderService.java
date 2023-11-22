package com.spiderdiary.Services;

import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.Entity.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderService {

    private final SpiderRepository spiderRepository;

    @Autowired
    public SpiderService(SpiderRepository spiderRepository) {
        this.spiderRepository = spiderRepository;
    }

    public Spider save(Spider spider) {
        return spiderRepository.save(spider);
    }
}
