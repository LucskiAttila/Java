package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.logic.command.room.RoomProperties;

import java.util.Set;

public class AttachPriceComponentMovieCommand extends AdminAbstractMovieCommand{

    private final String name;
    private final String title;

    private Set<MovieProperties> changes;

    public AttachPriceComponentMovieCommand(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @Override
    protected String operate() {
        if (isConvert()) {
            if (isValid_Attachment()) {
                if (setProperties()) {
                    update();
                    changes.add(MovieProperties.price_component);
                    return updateTrue();
                } else {
                    return attachFalse_reattaching(name, title);
                }
            } else {
                return attachFalse(getInValidError());
            }
        } else {
            return invalidUpdate(getBad_string());
        }
    }
}
