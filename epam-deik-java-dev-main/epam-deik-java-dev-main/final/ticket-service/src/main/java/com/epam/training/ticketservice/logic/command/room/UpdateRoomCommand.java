package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;

import java.util.HashSet;
import java.util.Set;

public class UpdateRoomCommand extends AdminAbstractRoomCommand  {

    private final String roomName;
    private final String numberOfRowsOfChairs;
    private final String numberOfColumnsOfChairs;

    private Room room;
    private Set<RoomProperties> changes;

    public UpdateRoomCommand(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        this.roomName = roomName;
        this.numberOfRowsOfChairs = numberOfRowsOfChairs;
        this.numberOfColumnsOfChairs = numberOfColumnsOfChairs;
    }

    @Override
    protected String operate() {
        if(isConvert()) {
            room = getRoomByName();
            if (room != null) {
                changes = getDifferent(room);
                if (changes != null) {
                    getComponents();
                    update();
                    return updateTrue();
                } else {
                    return updateFalse();
                }
            } else {
                return deleteFalse();
            }
        } else {
            return invalidUpdate(getBad_string());
        }
    }

    private Set<RoomProperties> getDifferent(Room room) {
        Set<RoomProperties> result = new HashSet<>();
        if(room.getNumberOfRowsOfChairs().equals(numberOfRowsOfChairs)) {
            result.add(RoomProperties.numberOfRowsOfChairs);
            if(room.getNumberOfColumnsOfChairs().equals(numberOfColumnsOfChairs)) {
                result.add(RoomProperties.numberOfColumnsOfChairs);
            }
        }
        else if(room.getNumberOfColumnsOfChairs().equals(numberOfColumnsOfChairs)) {
            result.add(RoomProperties.numberOfColumnsOfChairs);
        }
        return result;
    }
}
