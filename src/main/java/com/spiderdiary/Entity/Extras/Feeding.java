package com.spiderdiary.Entity.Extras;

import com.spiderdiary.Entity.Spider;
import jakarta.persistence.*;

@Entity
@Table(name="feeding")
public class Feeding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spider_id")
    private Spider spider;

    @ManyToOne
    @JoinColumn(name = "food_type_id")
    private FoodType foodType;

    @ManyToOne
    @JoinColumn(name = "rozmiar_id")
    private Rozmiar rozmiar;

    @Column(name = "feedings_per_week")
    private int feedingsPerWeek;

    public Spider getSpider() {
        return spider;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    public Rozmiar getRozmiar() {
        return rozmiar;
    }

    public void setRozmiar(Rozmiar rozmiar) {
        this.rozmiar = rozmiar;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public int getFeedingsPerWeek() {
        return feedingsPerWeek;
    }

    public void setFeedingsPerWeek(int feedingsPerWeek) {
        this.feedingsPerWeek = feedingsPerWeek;
    }
}
