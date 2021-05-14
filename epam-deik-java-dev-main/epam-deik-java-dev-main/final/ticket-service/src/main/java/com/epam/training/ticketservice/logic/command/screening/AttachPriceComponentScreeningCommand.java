package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.logic.command.movie.MovieProperties;
import com.epam.training.ticketservice.logic.command.room.RoomProperties;

import java.util.Set;

public class AttachPriceComponentScreeningCommand extends AdminAbstractScreeningCommand{

    private final String name;
    private final String title;
    private final String roomName;
    private final String startsDateAndTime;

    private Set<ScreeningProperties> changes;

    public AttachPriceComponentScreeningCommand(String name, String title, String roomName, String startsDateAndTime) {
        this.name = name;
        this.title = title;
        this.roomName = roomName;
        this.startsDateAndTime = startsDateAndTime;
    }

    @Override
    protected String operate() {
        if (checkDateFormat()) {
            if (isValid_Attachment()) {
                if (setProperties()) {
                    update();
                    changes.add(ScreeningProperties.price_component);
                    return updateTrue();
                } else {
                    return attachFalse_reattaching(name, title + " " + roomName + " " + startsDateAndTime);
                }
            } else {
                return attachFalse(getInValidError());
            }
        } else {
            return wrongDate();
        }
    }
}
