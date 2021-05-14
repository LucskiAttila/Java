package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.DeleteScreeningCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

public class DeleteScreeningCommandHandler {
    @ShellMethod(value = "Delete screening with the title of the movie, and the name of the room and the start time and date of the screening", key = "delete screen")
    public String deleteScreening(String title, String roomName, String startsDateAndTime) {
        DeleteScreeningCommand command = new DeleteScreeningCommand(title, roomName, startsDateAndTime);
        return command.execute();
    }
}