package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository <Room, String>{
    Room findByroomName(String roomName);
}
