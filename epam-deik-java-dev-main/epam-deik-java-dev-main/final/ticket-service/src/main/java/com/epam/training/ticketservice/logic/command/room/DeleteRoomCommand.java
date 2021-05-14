package com.epam.training.ticketservice.logic.command.room;

public class DeleteRoomCommand extends AdminAbstractRoomCommand {

    private final String roomName;

    public DeleteRoomCommand(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String operate() {
        if(isValid()) {
            delete();
            return deleteTrue();
        }
        else {
            return deleteFalse();
        }
    }
}
