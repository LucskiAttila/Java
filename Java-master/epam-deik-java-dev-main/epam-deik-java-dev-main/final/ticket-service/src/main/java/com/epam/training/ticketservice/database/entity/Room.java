package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Room {

    @Id
    String roomName;
    int numberOfRowsOfChairs;
    int numberOfColumnsOfChairs;
    @OneToMany
    List<PriceComponent> components;

    protected Room() {
    }

    public Room(String roomName, int numberOfRowsOfChairs, int numberOfColumnsOfChairs,
                List<PriceComponent> components) {
        this.roomName = roomName;
        this.numberOfRowsOfChairs = numberOfRowsOfChairs;
        this.numberOfColumnsOfChairs = numberOfColumnsOfChairs;
        this.components = components;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getNumberOfRowsOfChairs() {
        return numberOfRowsOfChairs;
    }

    public int getNumberOfColumnsOfChairs() {
        return numberOfColumnsOfChairs;
    }

    public List<PriceComponent> getComponents() {
        return components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return numberOfRowsOfChairs == room.numberOfRowsOfChairs
                && numberOfColumnsOfChairs == room.numberOfColumnsOfChairs
                && Objects.equals(roomName, room.roomName) && Objects.equals(components, room.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs, components);
    }
}

