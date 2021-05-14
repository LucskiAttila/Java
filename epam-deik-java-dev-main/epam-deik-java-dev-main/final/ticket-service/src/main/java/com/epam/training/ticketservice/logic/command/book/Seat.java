package com.epam.training.ticketservice.logic.command.book;

public class Seat {
    private int row_number;
    private int column_number;

    public Seat(int row_number, int column_number) {
        this.row_number = row_number;
        this.column_number = column_number;
    }

    public int getRow_number() {
        return row_number;
    }
    public int getColumn_number() {
        return column_number;
    }
}
