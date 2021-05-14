package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.CreateScreeningCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

public class CreateScreeningCommandHandler {
    @ShellMethod(value = "Create room with its name, and the row and column number of charis", key = "create screen")
    public String createScreening(String title, String roomName, String startsDateAndTime) {
        CreateScreeningCommand command = new CreateScreeningCommand(title, roomName, startsDateAndTime);
        return command.execute();
    }
}
