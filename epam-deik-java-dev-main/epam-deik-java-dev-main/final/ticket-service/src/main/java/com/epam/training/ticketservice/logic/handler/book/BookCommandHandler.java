package com.epam.training.ticketservice.logic.handler.book;


import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BookCommandHandler {

    @ShellMethod(value = "Books seats for a valid screening", key = "book")
    public String Book(String title, String roomName, String startsDateAndTime, String seats) {
        BookCommand command = new BookCommand(title, roomName, startsDateAndTime, seats, true);
        return command.execute();
    }
}
