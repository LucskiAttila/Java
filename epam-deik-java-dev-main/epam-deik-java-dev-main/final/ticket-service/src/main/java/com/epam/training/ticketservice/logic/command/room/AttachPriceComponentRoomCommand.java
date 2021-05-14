package com.epam.training.ticketservice.logic.command.room;

import java.util.Set;

public class AttachPriceComponentRoomCommand extends AdminAbstractRoomCommand{

    private final String name;
    private final String roomName;

    private Set<RoomProperties> changes;

    public AttachPriceComponentRoomCommand(String name, String roomName) {
        this.name = name;
        this.roomName = roomName;
    }

    @Override
    protected String operate() {
        if (isConvert()) {
            if (isValid_Attachment()) {
                if (setProperties()) {
                    update();
                    changes.add(RoomProperties.price_component);
                    return updateTrue();
                } else {
                    return attachFalse_reattaching(name, roomName);
                }
            } else {
                return attachFalse(getInValidError());
            }
        } else {
            return invalidUpdate(getBad_string());
        }
    }
}
