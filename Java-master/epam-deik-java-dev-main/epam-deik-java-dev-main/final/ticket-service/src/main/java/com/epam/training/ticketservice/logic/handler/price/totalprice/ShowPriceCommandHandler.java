package com.epam.training.ticketservice.logic.handler.price.totalprice;

import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShowPriceCommandHandler {

    private final BookCommand bookCommand;

    public ShowPriceCommandHandler(BookCommand bookCommand) {
        this.bookCommand = bookCommand;
    }

    @ShellMethod(value = "show price for the seats", key = "show price for")
    public String ShowPriceComponent(String title, String roomName, String startsDateAndTime, String seats) {
        String result = bookCommand.operate(title, roomName, startsDateAndTime, seats, false);
        return result;
    }
}
