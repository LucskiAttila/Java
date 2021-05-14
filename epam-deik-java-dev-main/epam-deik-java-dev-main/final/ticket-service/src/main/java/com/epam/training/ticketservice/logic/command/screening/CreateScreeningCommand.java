package com.epam.training.ticketservice.logic.command.screening;

import java.util.List;

public class CreateScreeningCommand extends AdminAbstractScreeningCommand {

    private final String title;
    private final String roomName;
    private final String startsDateAndTime;

    public CreateScreeningCommand(String title, String roomName, String startsDateAndTime) {
        this.title = title;
        this.roomName = roomName;
        this.startsDateAndTime = startsDateAndTime;
    }

    @Override
    protected String operate() {
        if (checkDateFormat()) {
            if (check()) {
                if (!isValid()) {
                    if(checkError()) {
                        formatDate();
                        setComponents();
                        save();
                        return createTrue();
                    } else {
                        return create_collusion(getBad_date());
                    }
                } else {
                    return createFalse();
                }
            }
            else {
                return wrongProperties(getInValidError());
            }
        }
        else {
            return wrongDate();
        }
    }
}
