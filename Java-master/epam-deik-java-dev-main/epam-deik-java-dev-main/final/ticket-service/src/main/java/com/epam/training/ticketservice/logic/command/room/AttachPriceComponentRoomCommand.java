package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachPriceComponentRoomCommand {

    @Value("${ATTACH_MORE}")
    private boolean canAttachMore;

    private List<PriceComponent> components;

    private String permissionError;
    private String invalidError;

    private Room room;
    private PriceComponent priceComponent;

    public void setCanAttachMore(boolean canAttachMore) {
        this.canAttachMore = canAttachMore;
    }

    private final RoomRepository roomRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public AttachPriceComponentRoomCommand(RoomRepository roomRepository,
                                           UserRepository userRepository,
                                           PriceComponentRepository priceComponentRepository) {
        this.roomRepository = roomRepository;
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String roomName) {
        if (hasPermission()) {
            if (isValid(name, roomName)) {
                boolean isDuplication = isComponentValid(name);
                if (canAttachMore) {
                    save(roomName);
                    if (isDuplication) {
                        return "okDuplicate";
                    } else {
                        return "ok";
                    }
                } else {
                    if (!isDuplication) {
                        save(roomName);
                        return "ok";
                    } else {
                        return "more";
                    }

                }
            } else {
                return invalidError;
            }
        } else {
            return permissionError;
        }
    }

    private void save(String roomName) {
        components.add(priceComponent);
        roomRepository.delete(room);
        roomRepository.save(new Room(roomName,
                room.getNumberOfRowsOfChairs(),
                room.getNumberOfColumnsOfChairs(), components));
    }

    private boolean isComponentValid(String name) {
        components = room.getComponents();
        int size = components.size();
        for (int i = 0; i < size; i++) {
            if (name.equals(components.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(String name, String roomName) {
        room =  roomRepository.findByRoomName(roomName);
        priceComponent = priceComponentRepository.findByName(name);
        if (room == null) {
            invalidError = "first";
            if (priceComponent == null) {
                invalidError = "all";
            }
            return false;
        }
        if (priceComponent == null) {
            invalidError = "second";
            return false;
        }
        return true;
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
}
