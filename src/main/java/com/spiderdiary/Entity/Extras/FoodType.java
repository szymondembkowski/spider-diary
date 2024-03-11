package com.spiderdiary.Entity.Extras;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="food_type")
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name")
    private String foodName;

    @OneToMany(mappedBy = "foodType")
    private List<Feeding> feedings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}