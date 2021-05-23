package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.AttachPriceComponentRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@ShellComponent
public class AttachPriceComponentRoomCommandHandler {

    private final AttachPriceComponentRoomCommand attachPriceComponentRoomCommand;

    public AttachPriceComponentRoomCommandHandler(AttachPriceComponentRoomCommand attachPriceComponentRoomCommand) {
        this.attachPriceComponentRoomCommand = attachPriceComponentRoomCommand;
    }

    @ShellMethod(value = "attach valid price component to a valid room", key = "attach price component to room")
    @Transactional
    public String attachPriceComponentRoom(String name, String roomName) {
        String result = attachPriceComponentRoomCommand.operate(name, roomName);
        switch (result) {
            case "first":
                return StringUtils.capitalize(roomName) + " room doesn't exists";
            case "second":
                return StringUtils.capitalize(name) + " price component doesn't exists";
            case "all":
                return StringUtils.capitalize(roomName) + " room, " + name + " price component doesn't exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "more":
                return StringUtils.capitalize(name) + " component is already attached to " + roomName;
            case "okDuplicate":
                return StringUtils.capitalize(name) + " component is successfully attached again to " + roomName;
            default:
                return StringUtils.capitalize(name) + " component is successfully attached to " + roomName;
        }
    }
}
