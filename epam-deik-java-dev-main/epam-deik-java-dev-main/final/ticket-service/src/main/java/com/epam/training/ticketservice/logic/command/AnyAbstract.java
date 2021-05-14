package com.epam.training.ticketservice.logic.command;

import com.epam.training.ticketservice.database.entity.Movie;

import java.util.List;

public abstract class AnyAbstract extends AnyText implements Command{

    @Override
    public String execute() {
        return operate();
    }

    protected abstract List list();

    protected abstract String operate();
}
