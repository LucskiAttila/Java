package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListRoomsCommand {

    private final RoomRepository roomRepository;

    public ListRoomsCommand(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> operate() {
        return roomRepository.findAll();
    }
}
