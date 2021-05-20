package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteRoomCommand {

    private String permission_error;
    private Room room;

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public DeleteRoomCommand(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public String operate(String roomName) {
        if (hasPermission()) {
            if (isValid(roomName)) {
                delete();
                return "ok";
            } else {
                return "exist";
            }
        } else {
            return permission_error;
        }
    }

    private void delete() {
        roomRepository.delete(room);
    }

    private boolean hasPermission() {
        User user = userRepository.findByIsSigned(true);
        if (user != null) {
            if (user.getIsAdmin()) {
                return true;
            }
            else {
                permission_error = "admin";
                return false;
            }
        }
        else {
            permission_error = "sign";
            return false;
        }
    }

    private boolean isValid(String roomName) {
        room = roomRepository.findByRoomName(roomName);
        return room != null;
    }
}

