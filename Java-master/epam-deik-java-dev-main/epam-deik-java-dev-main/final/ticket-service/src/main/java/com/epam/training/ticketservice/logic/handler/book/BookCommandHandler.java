package com.epam.training.ticketservice.logic.handler.book;

import com.epam.training.ticketservice.database.entity.Seat;
import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class BookCommandHandler {

    private final BookCommand bookCommand;

    public BookCommandHandler(BookCommand bookCommand) {
        this.bookCommand = bookCommand;
    }

    @ShellMethod(value = "Books seats for a valid screening", key = "book")
    public String book(String title, String roomName, String startsDateAndTime, String seats) {
        List<Object> result = bookCommand.operate(title, roomName, startsDateAndTime, seats, true);
        if ("exist".equals(result.get(0))) {
            return "Seat " + ((Seat) result.get(1)).getRowNumber() + ", "
                    + ((Seat) result.get(1)).getColumnNumber() + "does not exist";
        } else if ("taken".equals(result.get(0))) {
            return "Seat " + ((Seat) result.get(1)).getRowNumber() + ", "
                    + ((Seat) result.get(1)).getColumnNumber() + "is already taken";
        } else if ("sign".equals(result.get(0))) {
            return "You are not signed in";
        } else if ("admin".equals(result.get(0))) {
            return "Signed in with privileged account " + result.get(1);
        } else if ("movie".equals(result.get(0))) {
            return "Invalid movie title " + title;
        } else if ("room".equals(result.get(0))) {
            return "Invalid room name " + roomName;
        } else if ("format".equals(result.get(0))) {
            return "Invalid date format";
        } else if ("date".equals(result.get(0))) {
            return "Invalid date " + startsDateAndTime + "this would be " + result.get(1);
        } else if ("screening".equals(result.get(0))) {
            return "Screening doesn't exist " + title + " " + roomName + " " + startsDateAndTime;
        } else if ("row".equals(result.get(0))) {
            return "Bad input in row numbers, missing row in " + seats;
        } else if ("column".equals(result.get(0))) {
            return "Bad input in column numbers, missing column in " + seats;
        } else if (",".equals(result.get(0))) {
            return "Bad input too much commas in "
                    + seats + " at position " + result.get(1);
        } else if ("book".equals(result.get(0))) {
            return "Seats booked: " + formatSeats(((List<Seat>) result.get(1)))
                    + "; the price for this booking is " + result.get(2) + " " + result.get(3);
        } else {
            return "Invalid character in " + seats + " " + result.get(0) + " at position " + result.get(1);
        }
    }

    private String formatSeats(List<Seat> seats) {
        int size = seats.size();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append("(").append(seats.get(i).getRowNumber()).append(",")
                    .append(seats.get(i).getColumnNumber()).append(")");
            if (i != seats.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
