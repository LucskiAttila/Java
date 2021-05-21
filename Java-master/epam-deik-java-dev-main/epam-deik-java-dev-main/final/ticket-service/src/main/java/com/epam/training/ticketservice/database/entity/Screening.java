package com.epam.training.ticketservice.database.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(ScreeningId.class)
public class Screening {

    @ManyToOne
    @Id
    Movie movie;
    @ManyToOne
    @Id
    Room room;
    @Id
    Date startsDateTime;
    @OneToMany
    List<PriceComponent> components;

    protected Screening() {}

    public Screening(Movie movie, Room room, Date startsDateTime, List<PriceComponent> components) {
        this.movie = movie;
        this.room = room;
        this.startsDateTime = startsDateTime;
        this.components = components;
    }
    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public Date getStartsDateTime() {
        return startsDateTime;
    }

    public List<PriceComponent> getComponents() {
        return components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return Objects.equals(movie, screening.movie) && Objects.equals(room, screening.room) && Objects.equals(startsDateTime, screening.startsDateTime) && Objects.equals(components, screening.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, room, startsDateTime, components);
    }
}
