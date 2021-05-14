package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.room.ListScreeningsCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ListScreeningsCommandHandler {

    @ShellMethod(value = "List screenings with properties", key = "list screenings")
    public String listScreenings() {
        ListScreeingsCommand command = new ListScreeningsCommand();
        return command.execute();
    }
}
