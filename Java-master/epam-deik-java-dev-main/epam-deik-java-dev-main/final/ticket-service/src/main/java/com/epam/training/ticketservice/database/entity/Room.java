package com.epam.training.ticketservice.database.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    String roomName;
    int numberOfRowsOfChairs;
    int numberOfColumnsOfChairs;
    @OneToMany
    //@LazyCollection(LazyCollectionOption.FALSE)
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

