package com.epam.training.ticketservice.logic.handler.price.priceomponent.attach;


import com.epam.training.ticketservice.logic.command.room.AttachPriceComponentRoomCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AttachPriceComponentRoomCommandHandler {

    @ShellMethod(value = "attach valid price component to a valid room", key = "attach price component to room")
    public String AttachPriceComponentRoom(String name, String roomName) {
        AttachPriceComponentRoomCommand command = new AttachPriceComponentRoomCommand(name, roomName);
        return command.execute();
    }
}
