package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
    Room findByRoomName(String roomName);
}
