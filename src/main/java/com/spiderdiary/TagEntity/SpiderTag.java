package com.spiderdiary.TagEntity;

import com.spiderdiary.Entity.Spider;
import jakarta.persistence.*;

@Entity
@Table(name = "spider_tag")
public class SpiderTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spider_id")
    private Spider spider;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // Getters i Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Spider getSpider() {
        return spider;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
