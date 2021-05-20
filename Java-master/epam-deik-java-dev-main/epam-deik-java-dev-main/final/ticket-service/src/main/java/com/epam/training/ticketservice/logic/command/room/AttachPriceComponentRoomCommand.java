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
public class AttachPriceComponentRoomCommand{

    @Value("${ATTACH_MORE}")
    private boolean canAttachMore;

    private String permission_error;
    private String invalid_error;

    private Room room;
    private PriceComponent priceComponent;

    private final RoomRepository roomRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public AttachPriceComponentRoomCommand(RoomRepository roomRepository, UserRepository userRepository, PriceComponentRepository priceComponentRepository) {
        this.roomRepository = roomRepository;
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String roomName) {
        if (hasPermission()) {
            if (isValid(name, roomName)) {
                boolean isDuplication = !isComponentValid(name);
                if (canAttachMore) {
                    save();
                    if (isDuplication) {
                        return "ok_duplicate";
                    } else {
                        return "ok";
                    }
                } else {
                    if (!isDuplication) {
                        save();
                        return "ok";
                    } else {
                        return "more";
                    }

                }
            } else {
                return invalid_error;
            }
        } else {
            return permission_error;
        }
    }

    private void save() {
        List<PriceComponent> components = room.getComponents();
        components.add(priceComponent);
        roomRepository.delete(room);
        roomRepository.save(new Room (room.getRoomName(), room.getNumberOfRowsOfChairs(), room.getNumberOfColumnsOfChairs(), components));
    }

    private boolean isComponentValid(String name) {
        for (PriceComponent priceComponent : room.getComponents()) {
            if (name.equals(priceComponent.getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(String name, String roomName) {
        room =  roomRepository.findByRoomName(roomName);
        priceComponent = priceComponentRepository.findByName(name);
        if (room == null) {
            invalid_error = "room";
            if (priceComponent == null) {
                invalid_error = "all";
            }
            return false;
        }
        if (priceComponent == null) {
            invalid_error = "component";
            return false;
        }
        return true;
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
}
