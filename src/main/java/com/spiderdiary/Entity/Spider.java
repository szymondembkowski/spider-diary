package com.spiderdiary.Entity;

import com.spiderdiary.Entity.Extras.Feeding;
import com.spiderdiary.Entity.Extras.Rozmiar;
import com.spiderdiary.TempForms.Gender;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rozmiar_id")
    private Rozmiar rozmiar;

    @OneToMany(mappedBy = "spider", cascade = CascadeType.ALL)
    private List<Feeding> feedings;


    public List<Feeding> getFeedings() {
        return feedings;
    }

    public void setFeedings(List<Feeding> feedings) {
        for (Feeding feeding : feedings) {
            feeding.setSpider(this);
        }
        this.feedings = feedings;
    }

// ----------------------------------------------------------------

    public Spider() {
        this.feedings = new ArrayList<>();
    }

    public void addFeeding(Feeding feeding) {
        feedings.add(feeding);
        feeding.setSpider(this);
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRozmiar(Rozmiar rozmiar) {
        this.rozmiar = rozmiar;
    }

    public Rozmiar getRozmiar(){
        return rozmiar;
    }
}