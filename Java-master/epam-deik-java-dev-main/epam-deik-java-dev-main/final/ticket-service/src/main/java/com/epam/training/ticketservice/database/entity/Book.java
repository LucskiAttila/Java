package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookId;
    @OneToOne
    private Screening screening;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;
    private int price;

    protected Book() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return bookId == book.bookId && price == book.price
                && Objects.equals(screening, book.screening) && Objects.equals(seats, book.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, screening, seats, price);
    }
}
