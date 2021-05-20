package com.epam.training.ticketservice.database.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//import lombook.AllArgsConstructor;
//import lombook.NoArgsConstructor;
//import lombook.Generated;

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
    //@LazyCollection(LazyCollectionOption.FALSE)
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
}
