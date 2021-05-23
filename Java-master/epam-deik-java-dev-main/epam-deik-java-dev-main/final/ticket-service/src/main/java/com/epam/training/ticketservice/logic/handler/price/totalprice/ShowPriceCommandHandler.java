package com.epam.training.ticketservice.logic.handler.price.totalprice;

import com.epam.training.ticketservice.database.entity.Seat;
import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ShowPriceCommandHandler {

    private final BookCommand bookCommand;

    public ShowPriceCommandHandler(BookCommand bookCommand) {
        this.bookCommand = bookCommand;
    }

    @ShellMethod(value = "show price for the seats", key = "show price for")
    public String showPriceComponent(String title, String roomName, String startsDateAndTime, String seats) {
        List<Object> result = bookCommand.operate(title, roomName, startsDateAndTime, seats, false);
        if ("exist".equals(result.get(0))) {
            return "Seat " + ((Seat) result.get(1)).getRowNumber() + ", "
                    + ((Seat) result.get(1)).getColumnNumber() + "does not exist";
        } else if ("taken".equals(result.get(0))) {
            return "Seat " + ((Seat) result.get(1)).getRowNumber() + ", "
                    + ((Seat) result.get(1)).getColumnNumber() + "is already taken";
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
            return "Bad input too much commas in " + seats + " at position " + result.get(1);
        } else if ("show".equals(result.get(0))) {
            return "The price for this booking would be " + result.get(1) + " " + result.get(2);
        } else {
            return "Invalid character in " + seats + " " + result.get(0) + " at position " + result.get(1);
        }
    }
}
