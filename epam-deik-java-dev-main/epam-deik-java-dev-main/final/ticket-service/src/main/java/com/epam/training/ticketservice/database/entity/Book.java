package com.epam.training.ticketservice.database.entity;

import com.epam.training.ticketservice.logic.command.book.Seat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @OneToOne
    private Screening screening;
    private List<Seat> seats;
    private int price;

    protected Book() {}

    public Book(Screening screening, List<Seat> seats, int price) {
        this.screening = screening;
        this.seats = seats;
        this.price = price;
    }

    public List<Seat> getSeats() {
        return seats;
    }
    public int getPrice() {
        return price;
    }
    public Screening getScreening() {
        return screening;
    }
}
