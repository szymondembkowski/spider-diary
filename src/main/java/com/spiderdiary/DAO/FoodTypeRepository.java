package com.spiderdiary.DAO;

import com.spiderdiary.Entity.Extras.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    List<FoodType> findAll();

    List<FoodType> findAllByOrderByFoodNameAsc();

}
