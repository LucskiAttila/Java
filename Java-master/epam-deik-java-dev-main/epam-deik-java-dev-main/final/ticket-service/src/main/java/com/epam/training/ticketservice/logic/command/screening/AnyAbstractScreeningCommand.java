package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.logic.command.AnyAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AnyAbstractScreeningCommand extends AnyAbstract {

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    List<Screening> screenings;

    @Autowired
    MovieRepository movieRepository;

    private String type = "screenings";

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected List<Screening> list() {
        return (List<Screening>) screeningRepository.findAll();
    }

    @Override
    protected String getElements() {
        String result = "";
        for (Screening screening : screenings) {
            Movie movie = screening.getMovie();
            result += capitalize(movie.getTitle()) + " (" + movie.getGenre() + ", " + movie.getDurationInMinutes() + "), screened in room " + screening.getRoom().getRoomName() + ", at " + screening.getStartsDateTime() + "\n";
        }
        return result.toString().toString();
    }
}
