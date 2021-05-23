package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateRoomCommand {

    private String badString;
    private String permissionError;

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private final String emptyString = "";

    @Value("#{'${DIGITS}'.split(',')}")
    List<Character> digits;

    public void setDigits(List<Character> digits) {
        this.digits = digits;
    }

    public CreateRoomCommand(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public String operate(String roomName, String numberOfRowsOfChairs, String numberOfColumnsOfChairs) {
        if (hasPermission()) {
            if (isConvert(numberOfRowsOfChairs, numberOfColumnsOfChairs)) {
                if (isValid(roomName)) {
                    int numberOfRowsOfChairsFormatInt = convertNumberOfChairs(numberOfRowsOfChairs);
                    int numberOfColumnsOfChairsFormatInt = convertNumberOfChairs(numberOfColumnsOfChairs);
                    roomRepository.save(new Room(roomName, numberOfRowsOfChairsFormatInt,
                            numberOfColumnsOfChairsFormatInt, new ArrayList<PriceComponent>()));
                    return "ok";
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

    private boolean isValid(String roomName) {
        return roomRepository.findByRoomName(roomName) == null;
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
}
