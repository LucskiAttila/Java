package com.epam.training.ticketservice.logic.handler.book;


import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BookCommandHandler {

    private final BookCommand bookCommand;

    public BookCommandHandler(BookCommand bookCommand) {
        this.bookCommand = bookCommand;
    }

    @ShellMethod(value = "Books seats for a valid screening", key = "book")
    public String Book(String title, String roomName, String startsDateAndTime, String seats) {
        String result = bookCommand.operate(title, roomName, startsDateAndTime, seats, true);
        return result;
    }
}
