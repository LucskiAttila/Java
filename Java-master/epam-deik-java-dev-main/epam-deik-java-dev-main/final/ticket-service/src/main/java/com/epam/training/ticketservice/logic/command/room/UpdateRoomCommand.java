package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateRoomCommand {

    private String permission_error;
    private String badString;

    private Room room;

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final String emptyString = "";

    @Value("#{'${DIGITS}'.split(',')}")
    List<Character> digits;

    public UpdateRoomCommand(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public String operate(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        if (hasPermission()) {
            if (isConvert(numberOfRowsOfChairs, numberOfColumnsOfChairs)) {
                if (isValid(roomName)) {
                    int numberOfRowsOfChairs_int = convertNumberOfChairs(numberOfRowsOfChairs);
                    int numberOfColumnsOfChairs_int = convertNumberOfChairs(numberOfColumnsOfChairs);
                    String matching = getNotDifferent(room, numberOfRowsOfChairs_int, numberOfColumnsOfChairs_int);
                    if (!"all".equals(matching)) {
                        update(numberOfRowsOfChairs_int, numberOfColumnsOfChairs_int);
                    }
                    return matching;
                } else {
                    return "exist";
                }
            } else {
                return badString;
            }
        } else {
            return permission_error;
        }
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

    private void update(int numberOfRowsOfChairs_int, int numberOfColumnsOfChairs_int) {
        roomRepository.delete(room);
        roomRepository.save(new Room(room.getRoomName(), numberOfRowsOfChairs_int, numberOfColumnsOfChairs_int, room.getComponents()));
    }

    private boolean isValid(String roomName) {
        room = roomRepository.findByRoomName(roomName);
        return room != null;
    }

    private boolean isConvert(String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        badString = validConvertToInt(numberOfRowsOfChairs, numberOfColumnsOfChairs);
        return emptyString.equals(badString);
    }

    private String validConvertToInt(String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        for (int i = 0; i < numberOfRowsOfChairs.length(); i++) {
            if (!digits.contains(numberOfRowsOfChairs.charAt(i))) {
                return String.valueOf(numberOfRowsOfChairs.charAt(i));
            }
        }
        for (int i = 0; i < numberOfColumnsOfChairs.length(); i++) {
            if (!digits.contains(numberOfColumnsOfChairs.charAt(i))) {
                return String.valueOf(numberOfColumnsOfChairs.charAt(i));
            }
        }
        return emptyString;
    }

    private int convertNumberOfChairs(String numberOfChairs) {
        return Integer.parseInt(numberOfChairs);
    }

    private String getNotDifferent(Room room, int numberOfRowsOfChairs_int, int numberOfColumnsOfChairs_int) {
        String result = emptyString;
        if(room.getNumberOfRowsOfChairs() == numberOfRowsOfChairs_int) {
            result = "first";
            if(room.getNumberOfColumnsOfChairs() == numberOfColumnsOfChairs_int) {
                result = "all";
            }
        }
        else if(room.getNumberOfRowsOfChairs() == numberOfRowsOfChairs_int) {
            result = "second";
        }
        return result;
    }
}
