package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.logic.command.room.ListRoomsCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.util.List;

@ShellComponent
public class ListRoomsCommandHandler {

    private final ListRoomsCommand listRoomsCommand;

    public ListRoomsCommandHandler(ListRoomsCommand listRoomsCommand) {
        this.listRoomsCommand = listRoomsCommand;
    }

    @ShellMethod(value = "List rooms with properties", key = "list rooms")
    public String listRooms() {
        List<Room> rooms = listRoomsCommand.operate();
        if(rooms.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (Room room : rooms) {
                if (room == rooms.get(rooms.size() - 1)) {
                    result.append(StringUtils.capitalize("room ")).append(room.getRoomName()).append(" with ").append(getChairNumber(room)).append(" seats, ").append(room.getNumberOfRowsOfChairs()).append(" rows and ").append(room.getNumberOfColumnsOfChairs()).append(" columns");
                } else {
                    result.append(StringUtils.capitalize("room ")).append(room.getRoomName()).append(" with ").append(getChairNumber(room)).append(" seats, ").append(room.getNumberOfRowsOfChairs()).append(" rows and ").append(room.getNumberOfColumnsOfChairs()).append(" columns\n");
                }
            }
            return result.toString();
        }
    }

    private String getChairNumber(Room room) {
        return String.valueOf(room.getNumberOfColumnsOfChairs() * room.getNumberOfRowsOfChairs());
    }
}
