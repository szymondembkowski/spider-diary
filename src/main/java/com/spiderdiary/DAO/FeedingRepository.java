package com.spiderdiary.DAO;

import com.spiderdiary.Entity.Extras.Feeding;
import com.spiderdiary.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedingRepository extends JpaRepository<Feeding, Long> {
    List<Feeding> findAll();

    List<Feeding> findAllByOrderByFeedingsPerWeekAsc();

}
