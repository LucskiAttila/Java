package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Screening;

import java.util.List;

public class ListScreeningsCommand extends AnyAbstractScreeningCommand {

    @Override
    public String operate() {
        List<Screening> screenings = list();
        if (screenings != null) {
            return listTrue();
        }
        else {
            return listFalse(true);
        }
    }
}
