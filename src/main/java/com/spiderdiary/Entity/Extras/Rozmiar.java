package com.spiderdiary.Entity.Extras;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rozmiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wylinki;

    @Column(name = "dlugosc_ciala")
    private String dlugoscCiala;

    @OneToMany(mappedBy = "rozmiar")
    private List<Feeding> feedings;


    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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