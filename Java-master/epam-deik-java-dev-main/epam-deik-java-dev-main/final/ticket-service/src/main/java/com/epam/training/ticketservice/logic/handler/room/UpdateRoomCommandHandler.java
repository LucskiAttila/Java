package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.UpdateRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class UpdateRoomCommandHandler {

    private final UpdateRoomCommand updateRoomCommand;

    public UpdateRoomCommandHandler(UpdateRoomCommand updateRoomCommand) {
        this.updateRoomCommand = updateRoomCommand;
    }

    @ShellMethod(value = "Updates room with new number of rows and columns of the chairs", key = "update room")
    public String updateRoom(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        String result = updateRoomCommand.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);
        switch (result) {
            case "first":
                return "You successfully updated number of rows of charis of " + roomName;
            case "second":
                return "You successfully updated number of columns of charis of " + roomName;
            case "all":
                return "You add same properties";
            case "":
                return "You successfully updated all properties of " + roomName;
            case "exist":
                return StringUtils.capitalize(roomName) + " room is already exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            default:
                return "You add invalid integer " + result + " in number of chairs properties";
        }
    }
}
