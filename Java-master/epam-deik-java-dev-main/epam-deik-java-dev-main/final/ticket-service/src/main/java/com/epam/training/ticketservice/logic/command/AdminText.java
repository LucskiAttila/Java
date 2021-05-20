package com.epam.training.ticketservice.logic.command;


import java.util.List;

public abstract class AdminText implements Text{

    protected final String notAdmin() {
        return "You are signed in as not administrator";
    }

    protected final String notSigned() {
        return "You aren't signed in";
    }

    protected final String createTrue() {
        return capitalize(getType()) + " successfully created";
    }

    protected final String createFalse() {
        return String.format("This %s is already exist", getType());
    }

    protected final String attachFalse(String error) {
        return "You add invalid parameter: " + error;
    }

    protected final String attachFalse_reattaching(String name, String objectName) {
        return "You already attach this: " + name + " with " + objectName;
    }

    protected final String create_collusion(String collusion) {
        if(com.epam.training.ticketservice.logic.command.screening.ScreeningProperties.overlap.toString().equals(collusion)) {
            return "There is an overlapping screening";
        }
        else {
            return "This would start in the break period after another screening in this room";
        }
    }

    protected final String deleteTrue() {
        return capitalize(getType()) + " successfully deleted";
    }

    protected final String deleteFalse() {
        return String.format("This %s doesn't exists", getType());
    }

    protected final String updateTrue() {
        return modifiedProperties() + "modified";
    }

    protected final String updateFalse() {
        return "You add the same " + getType() + "properties";
    }

    protected final String invalidUpdate(String invalid) {
        return "You add invalid integer: " + invalid;
    }

    protected final String emptyString = "";

    protected final String wrongDate() {
        return "Bad date format";
    }

    protected final String wrongProperties(String worngs) {return "You added invalid:" + worngs;}

    public String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    protected abstract String modifiedProperties();
}
