package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListScreeningsCommand {

    private final ScreeningRepository screeningRepository;

    public ListScreeningsCommand(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<Screening> operate() {
        return screeningRepository.findAll();
    }
}
