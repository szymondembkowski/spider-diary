package com.spiderdiary.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "spider")
public class Spider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spider_name")
    private String spiderName;

    @ManyToOne
    @JoinColumn(name = "user_username", nullable = false)
    private User user;

// Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpiderName() {
        return spiderName;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}