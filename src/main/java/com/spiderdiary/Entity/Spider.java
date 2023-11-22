package com.spiderdiary.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "spider")
public class Spider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "molt_date")
    @Temporal(TemporalType.DATE)
    private Date moltDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", nullable = false)
    private User user;

    // Getters and setters

    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}