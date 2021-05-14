package com.epam.training.ticketservice.database.entity;

import java.io.Serializable;
import java.util.Objects;

public class ScreeningId implements Serializable {

    private String title;
    private String roomName;
    private String startsDateAndTime;

    protected ScreeningId() {}

    public ScreeningId(String title, String roomName, String startsDateAndTime) {
        this.title = title;
        this.roomName = roomName;
        this.startsDateAndTime = startsDateAndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreeningId that = (ScreeningId) o;
        return Objects.equals(title, that.title) && Objects.equals(roomName, that.roomName) && Objects.equals(startsDateAndTime, that.startsDateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, roomName, startsDateAndTime);
    }
}
