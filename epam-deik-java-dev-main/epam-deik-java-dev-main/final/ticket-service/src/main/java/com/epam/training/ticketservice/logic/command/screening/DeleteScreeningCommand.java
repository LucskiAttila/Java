package com.epam.training.ticketservice.logic.command.screening;

public class DeleteScreeningCommand extends AdminAbstractScreeningCommand {

    private final String title;
    private final String roomName;
    private final String startsDateAndTime;

    public DeleteScreeningCommand(String title ,String roomName, String startsDateAndTime) {
        this.title = title;
        this.roomName = roomName;
        this.startsDateAndTime = startsDateAndTime;
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
