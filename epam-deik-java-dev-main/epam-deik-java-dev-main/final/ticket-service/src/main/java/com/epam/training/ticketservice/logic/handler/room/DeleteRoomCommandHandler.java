package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.DeleteRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DeleteRoomCommandHandler {

    @ShellMethod(value = "Deletes a room by its name", key = "delete room")
    public String deleteRoom(String roomName) {
        DeleteRoomCommand command = new DeleteRoomCommand(roomName);
        return command.execute();
    }
}
