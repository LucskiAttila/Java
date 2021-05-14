package com.epam.training.ticketservice.logic.command.book;

import java.util.List;

public class Result {
    private List<Seat> seat;
    private String state;

    public Result(List<Seat> seat, String state) {
        this.seat = seat;
        this.state = state;
    }

    public String getState() {
        return state;
    }
    public List<Seat> getSeats() {
        return seat;
    }
}
