package com.epam.training.ticketservice.database.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {

    @Id
    String title;
    String genre;
    int durationInMinutes;
    @OneToMany
    //@LazyCollection(LazyCollectionOption.FALSE)
    List<PriceComponent> components;

    protected Movie() {}

    public Movie(String title, String genre, int durationInMinutes, List<PriceComponent> components) {
        this.title = title;
        this.genre = genre;
        this.durationInMinutes = durationInMinutes;
        this.components = components;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public List<PriceComponent> getComponents() {
        return components;
    }
}
