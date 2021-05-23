package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
public class Movie {

    @Id
    String title;
    String genre;
    int durationInMinutes;
    @OneToMany
    List<PriceComponent> components;

    protected Movie() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return durationInMinutes == movie.durationInMinutes && Objects.equals(title, movie.title)
                && Objects.equals(genre, movie.genre) && Objects.equals(components, movie.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, durationInMinutes, components);
    }
}
