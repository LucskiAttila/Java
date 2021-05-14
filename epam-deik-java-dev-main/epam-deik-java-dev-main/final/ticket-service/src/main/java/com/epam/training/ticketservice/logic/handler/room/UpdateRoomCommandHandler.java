package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.UpdateRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateRoomCommandHandler {

    @ShellMethod(value = "Updates room with new number of rows and columns of the chairs", key = "update room")
    public String updateRoom(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        UpdateRoomCommand command = new UpdateRoomCommand(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);
        return command.execute();
    }
}
