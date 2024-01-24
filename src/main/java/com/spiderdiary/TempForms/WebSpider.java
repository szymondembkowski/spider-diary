package com.spiderdiary.TempForms;

import com.spiderdiary.Entity.Extras.Feeding;
import com.spiderdiary.Entity.Extras.Rozmiar;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class WebSpider {

    private Long id;
    private String name;
    private String species;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date moltDate;
    private Gender gender;

    private Rozmiar rozmiar = new Rozmiar();
    private String wylinki;
    private String dlugoscCiala;

    private List<Feeding> feedings;

    private int feedingsPerWeek;
    private Long foodTypeId;

    private Long spiderId;


    // Getters and setters

    public Long getSpiderId() {
        return spiderId;
    }

    public void setSpiderId(Long spiderId) {
        this.spiderId = spiderId;
    }

    public int getFeedingsPerWeek() {
        return feedingsPerWeek;
    }

    public void setFeedingsPerWeek(int feedingsPerWeek) {
        this.feedingsPerWeek = feedingsPerWeek;
    }

    public Long getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(Long foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public Long getId() {
        return id;
    }

    public List<Feeding> getFeedings() {
        return feedings;
    }

    public void setFeedings(List<Feeding> feedings) {
        this.feedings = feedings;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Date getMoltDate() {
        return moltDate;
    }

    public void setMoltDate(Date moltDate) {
        this.moltDate = moltDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Rozmiar getRozmiar() {
        return rozmiar;
    }

    public void setRozmiar(Rozmiar rozmiar) {
        this.rozmiar = rozmiar;
    }

    public String getWylinki() {
        return wylinki;
    }

    public void setWylinki(String wylinki) {
        this.wylinki = wylinki;
    }

    public String getDlugoscCiala() {
        return dlugoscCiala;
    }

    public void setDlugoscCiala(String dlugoscCiala) {
        this.dlugoscCiala = dlugoscCiala;
    }
}