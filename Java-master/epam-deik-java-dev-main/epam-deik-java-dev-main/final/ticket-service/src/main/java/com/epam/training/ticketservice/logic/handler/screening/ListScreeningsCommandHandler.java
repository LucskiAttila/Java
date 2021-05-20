package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.logic.command.screening.ListScreeningsCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.util.List;

@ShellComponent
public class ListScreeningsCommandHandler {

    private final ListScreeningsCommand listScreeningsCommand;

    public ListScreeningsCommandHandler(ListScreeningsCommand listScreeningsCommand) {
        this.listScreeningsCommand = listScreeningsCommand;
    }

    @ShellMethod(value = "List screenings with properties", key = "list screenings")
    public String listScreenings() {
        List<Screening> screenings = listScreeningsCommand.operate();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        } else {
            StringBuilder result = new StringBuilder();
            for (Screening screening : screenings) {
                Movie movie = screening.getMovie();
                if (screening == screenings.get(screenings.size() - 1)) {
                    result.append(StringUtils.capitalize(movie.getTitle())).append(" (").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append("), screened in room ").append(screening.getRoom().getRoomName()).append(", at ").append(screening.getStartsDateTime());
                } else {
                    result.append(StringUtils.capitalize(movie.getTitle())).append(" (").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append("), screened in room ").append(screening.getRoom().getRoomName()).append(", at ").append(screening.getStartsDateTime()).append("\n");
                }
            }
            return result.toString();
        }
    }
}
