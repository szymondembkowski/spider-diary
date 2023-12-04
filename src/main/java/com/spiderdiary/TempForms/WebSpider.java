package com.spiderdiary.TempForms;


import com.spiderdiary.TagEntity.Tag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

public class WebSpider {

    private Long id;
    private String name;
    private String species;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date moltDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Set<Tag> tags;


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

    public Date getMoltDate() {
        return moltDate;
    }

    public void setMoltDate(Date moltDate) {
        this.moltDate = moltDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGenderLabel() {
        return gender != null ? gender.getLabel() : "";
    }

}