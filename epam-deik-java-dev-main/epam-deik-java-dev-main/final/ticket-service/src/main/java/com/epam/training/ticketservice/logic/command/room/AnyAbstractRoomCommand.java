package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.logic.command.AnyAbstract;

import java.util.List;

public abstract class AnyAbstractRoomCommand extends AnyAbstract {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    Room rooms;

    private String type = "rooms";

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected List<Room> list() {
        return roomRepository.findAll();
    }

    @Override
    protected String getElements() {
        StringBuilder result = new StringBuilder();
        for (Room room : rooms) {
            result.append(capitalize("room ")).append(room.getRoomName()).append(" with ").append(getChairNumber(room)).append(" seats, ").append(room.getNumberOfRowsOfChairs()).append(" rows and ").append(room.getNumberOfColumnsOfChairs()).append(" columns\n");
        }
        return result.toString().toString();
    }

    private String getChairNumber(Room room) {
        return String.valueOf(room.getNumberOfColumnsOfChairs() * room.getNumberOfRowsOfChairs());
    }
}
