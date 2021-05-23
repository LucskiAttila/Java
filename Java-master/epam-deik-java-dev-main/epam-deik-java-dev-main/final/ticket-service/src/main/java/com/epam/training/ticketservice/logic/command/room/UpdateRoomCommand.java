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

    private String permissionError;
    private String badString;

    private Room room;

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final String emptyString = "";

    @Value("#{'${DIGITS}'.split(',')}")
    List<Character> digits;

    public void setDigits(List<Character> digits) {
        this.digits = digits;
    }

    public UpdateRoomCommand(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public String operate(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        if (hasPermission()) {
            if (isConvert(numberOfRowsOfChairs, numberOfColumnsOfChairs)) {
                if (isValid(roomName)) {
                    int numberOfRowsOfChairsFormatInt = convertNumberOfChairs(numberOfRowsOfChairs);
                    int numberOfColumnsOfChairsFormatInt = convertNumberOfChairs(numberOfColumnsOfChairs);
                    String matching = getNotDifferent(room,
                            numberOfRowsOfChairsFormatInt,
                            numberOfColumnsOfChairsFormatInt);
                    if (!"all".equals(matching)) {
                        update(numberOfRowsOfChairsFormatInt, numberOfColumnsOfChairsFormatInt);
                    }
                    return matching;
                } else {
                    return "exist";
                }
            } else {
                return badString;
            }
        } else {
            return permissionError;
        }
    }

    private boolean hasPermission() {
        User user = userRepository.findByIsSigned(true);
        if (user != null) {
            if (user.getIsAdmin()) {
                return true;
            } else {
                permissionError = "admin";
                return false;
            }
        } else {
            permissionError = "sign";
            return false;
        }
    }

    private void update(int numberOfRowsOfChairsFormatInt, int numberOfColumnsOfChairsFormatInt) {
        roomRepository.delete(room);
        roomRepository.save(new Room(room.getRoomName(),
                numberOfRowsOfChairsFormatInt,
                numberOfColumnsOfChairsFormatInt,
                room.getComponents()));
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

    private String getNotDifferent(Room room,
                                   int numberOfRowsOfChairsFormatInt,
                                   int numberOfColumnsOfChairsFormatInt) {
        String result = emptyString;
        if (room.getNumberOfRowsOfChairs() == numberOfRowsOfChairsFormatInt) {
            result = "first";
            if (room.getNumberOfColumnsOfChairs() == numberOfColumnsOfChairsFormatInt) {
                result = "all";
            }
        } else if (room.getNumberOfColumnsOfChairs() == numberOfColumnsOfChairsFormatInt) {
            result = "second";
        }
        return result;
    }
}
