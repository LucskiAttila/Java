package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.CreateRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateRoomCommandHandler {

    @ShellMethod(value = "Create room with its name, and the row and column number of charis", key = "create room")
    public String createRoom(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        CreateRoomCommand command = new CreateRoomCommand(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);
        return command.execute();
    }
}
