package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.ListRoomsCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ListRoomsCommandHandler {

    @ShellMethod(value = "List rooms with properties", key = "list rooms")
    public String listRooms() {
        ListRoomsCommand command = new ListRoomsCommand();
        return command.execute();
    }
}
