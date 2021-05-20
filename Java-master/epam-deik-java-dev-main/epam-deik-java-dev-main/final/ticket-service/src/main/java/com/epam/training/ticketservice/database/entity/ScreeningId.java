package com.epam.training.ticketservice.database.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ScreeningId implements Serializable {


    private String movie;
    private String room;
    private Date startsDateTime;

    protected ScreeningId() {}

    public ScreeningId(String movie, String room, Date startsDateTime) {
        this.movie = movie;
        this.room = room;
        this.startsDateTime = startsDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreeningId that = (ScreeningId) o;
        return Objects.equals(movie, that.movie) && Objects.equals(room, that.room) && Objects.equals(startsDateTime, that.startsDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, room, startsDateTime);
    }
}
