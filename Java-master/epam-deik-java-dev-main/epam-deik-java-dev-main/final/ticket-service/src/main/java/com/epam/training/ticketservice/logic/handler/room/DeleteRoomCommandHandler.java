package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.DeleteRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class DeleteRoomCommandHandler {

    private final DeleteRoomCommand deleteRoomCommand;

    public DeleteRoomCommandHandler(DeleteRoomCommand deleteRoomCommand) {
        this.deleteRoomCommand = deleteRoomCommand;
    }

    @ShellMethod(value = "Deletes a room by its name", key = "delete room")
    public String deleteRoom(String roomName) {
        String result = deleteRoomCommand.operate(roomName);
        switch (result) {
            case "exist":
                return StringUtils.capitalize(roomName) + " room doesn't exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            default:
                return StringUtils.capitalize(roomName) + " room is successfully deleted";
        }
    }
}
