package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

//import lombook.AllArgsConstructor;
//import lombook.NoArgsConstructor;
//import lombook.Generated;

@Entity
@IdClass(ScreeningId.class)
public class Screening {

    @OneToOne
    @Id
    Movie movie;
    @OneToOne
    @Id
    Room room;
    @Id
    Date startsDateAndTime;
    List<PriceComponent> components;

    protected Screening() {}

    public Screening(Movie movie, Room room, Date startsDateAndTime, List<PriceComponent> components) {
        this.movie = movie;
        this.room = room;
        this.startsDateAndTime = startsDateAndTime;
        this.components = components;
    }
    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public Date getStartsDateAndTime() {
        return startsDateAndTime;
    }

    public List<PriceComponent> getComponents() {
        return components;
    }
}
