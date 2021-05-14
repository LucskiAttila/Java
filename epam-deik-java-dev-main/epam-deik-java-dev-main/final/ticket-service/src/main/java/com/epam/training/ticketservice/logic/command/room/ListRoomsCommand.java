package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;

import java.util.List;

public class ListRoomsCommand extends AnyAbstractRoomCommand {

    @Override
    public String operate() {
        List<Room> rooms = list();
        if (rooms != null) {
            return listTrue();
        }
        else {
            return listFalse(false);
        }
    }
}