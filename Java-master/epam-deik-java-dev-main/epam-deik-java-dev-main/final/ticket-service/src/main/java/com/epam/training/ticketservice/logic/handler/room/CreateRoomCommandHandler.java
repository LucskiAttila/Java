package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.CreateRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class CreateRoomCommandHandler {

    private final CreateRoomCommand createRoomCommand;

    public CreateRoomCommandHandler(CreateRoomCommand createRoomCommand) {
        this.createRoomCommand = createRoomCommand;
    }

    @ShellMethod(value = "Create room with its name, and the row and column number of charis", key = "create room")
    public String createRoom(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        String result = createRoomCommand.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);
        switch (result) {
            case "ok":
                return StringUtils.capitalize(roomName) + " room is successfully created";
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
