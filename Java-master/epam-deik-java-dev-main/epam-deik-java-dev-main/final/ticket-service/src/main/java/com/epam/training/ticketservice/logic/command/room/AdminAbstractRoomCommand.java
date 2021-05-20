package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.logic.command.AdminAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AdminAbstractRoomCommand extends AdminAbstract {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    PriceComponentRepository priceComponentRepository;

    @Autowired
    String roomName;

    @Autowired
    String numberOfRowsOfChairs;

    @Autowired
    String numberOfColumnsOfChairs;

    @Autowired
    Set<RoomProperties> changes;

    @Autowired
    String name;

    private List<PriceComponent> components;

    private String type = "room";

    private Room room;

    private PriceComponent priceComponent;

    private int numberOfRowsOfChairs_int;

    private int numberOfColumnsOfChairs_int;

    private String invalidError;

    private String badString;

    protected boolean setProperties() {
        components = room.getComponents();
        if (!components.contains(priceComponent)) {
            numberOfRowsOfChairs_int = room.getNumberOfRowsOfChairs();
            numberOfColumnsOfChairs_int = room.getNumberOfColumnsOfChairs();
            components.add(priceComponent);
            return true;
        } else {
            return false;
        }
    }

    protected void getComponents() {
        components = room.getComponents();
    }

    protected void setComponents() {
        components = new ArrayList<PriceComponent>();
    }

    protected void convertRowsAndColumns() {
        numberOfRowsOfChairs_int = convertToInt(numberOfRowsOfChairs);
        numberOfColumnsOfChairs_int = convertToInt(numberOfColumnsOfChairs);
    }

    protected String getBad_string() {
        return badString;
    }

    protected boolean isConvert() {
        String bad_integer_rows = validConvertToInt(numberOfRowsOfChairs);
        String bad_integer_columns = validConvertToInt(numberOfColumnsOfChairs);
        if (emptyString.equals(bad_integer_rows)) {
            if (emptyString.equals(bad_integer_columns)) {
                convertRowsAndColumns();
                badString = bad_integer_rows + " " + bad_integer_columns;
            }
            badString = bad_integer_rows;
            return false;
        } else if(emptyString.equals(bad_integer_columns)) {
            badString = bad_integer_columns;
            return false;
        }
        return true;
    }

    protected boolean isValid() {
        return roomRepository.findByRoomName(roomName) != null;
    }

    protected boolean isValid_Attachment() {
        invalidError = emptyString;
        room = roomRepository.findByRoomName(roomName);
        priceComponent = priceComponentRepository.findByName(name);
        if (room == null) {
            invalidError += RoomProperties.room.toString();
            if (priceComponent == null) {
                invalidError += " " + RoomProperties.price_component.toString();
            }
            return false;
        } else if(priceComponent == null) {
            invalidError += RoomProperties.price_component.toString();
            return false;
        } else{
            return true;
        }
    }

    protected String getInValidError() {
        return invalidError;
    }

    protected Room getRoomByName() {
        return roomRepository.findByRoomName(roomName);
    }

    protected void delete() {
        roomRepository.delete(room);
    }

    protected void save() {
        roomRepository.save(new Room(roomName, numberOfRowsOfChairs_int, numberOfColumnsOfChairs_int, components));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected String modifiedProperties() {
        StringBuilder result = new StringBuilder();
        for (RoomProperties change : changes) {
            result.append(capitalize((change.toString()))).append(" ");
        }
        return result.toString();
    }
}
