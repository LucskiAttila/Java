package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Room {

    @Id
    String roomName;
    int numberOfRowsOfChairs;
    int numberOfColumnsOfChairs;
    List<PriceComponent> components;

    protected Room() {}

    public Room(String roomName, int numberOfRowsOfChairs, int numberOfColumnsOfChairs, List<PriceComponent> components) {
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
}

