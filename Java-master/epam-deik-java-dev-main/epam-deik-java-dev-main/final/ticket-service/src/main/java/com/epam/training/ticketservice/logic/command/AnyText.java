package com.epam.training.ticketservice.logic.command;

public abstract class AnyText implements Text{

    protected final String listTrue() {
        return getElements();
    }

    protected final String listFalse(boolean screeningOptional) {
        String result = "There are no" + getType();
        if (screeningOptional) {
            result += "at the moment";
        }
        return result;
    }

    public String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    protected abstract String getElements();
}
