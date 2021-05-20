package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seat {
    private int row_number;
    private int column_number;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Seat(int row_number, int column_number) {
        this.row_number = row_number;
        this.column_number = column_number;
    }

    public Seat() {

    }

    public int getRow_number() {
        return row_number;
    }
    public int getColumn_number() {
        return column_number;
    }

    public void setRow_number(int row_number) {
        this.row_number = row_number;
    }

    public void setColumn_number(int column_number) {
        this.column_number = column_number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
