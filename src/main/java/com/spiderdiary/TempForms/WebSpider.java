package com.spiderdiary.TempForms;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WebSpider {

    private String name;
    private String species;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date latestMolt;

    // Getters and setters

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

    public Date getLatestMolt() {
        return latestMolt;
    }

    public void setLatestMolt(Date latestMolt) {
        this.latestMolt = latestMolt;
    }
}
