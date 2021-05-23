package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rowNumber;
    private int columnNumber;

    public Seat(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public Seat() {
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return rowNumber == seat.rowNumber && columnNumber == seat.columnNumber && Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rowNumber, columnNumber);
    }
}
