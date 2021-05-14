package com.epam.training.ticketservice.logic.command.room;


public class CreateRoomCommand extends AdminAbstractRoomCommand {

    private final String roomName;
    private final String numberOfRowsOfChairs;
    private final String numberOfColumnsOfChairs;

    public CreateRoomCommand(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        this.roomName = roomName;
        this.numberOfRowsOfChairs = numberOfRowsOfChairs;
        this.numberOfColumnsOfChairs = numberOfColumnsOfChairs;
    }

    @Override
    protected String operate() {
        if(isConvert()) {
            if (!isValid()) {
                setComponents();
                save();
                return createTrue();
            } else {
                return createFalse();
            }
        } else {
            return invalidUpdate(getBad_string());
        }
    }
}
