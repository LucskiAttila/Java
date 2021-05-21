package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.logic.command.screening.ListScreeningsCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

@ShellComponent
public class ListScreeningsCommandHandler {

    @Value("${DATE_FORMAT}")
    private String dateFormat;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

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
            for (int i = 0; i < screenings.size(); i++) {
                Movie movie = screenings.get(i).getMovie();
                if (i == screenings.size() - 1) {
                    result.append(StringUtils.capitalize(movie.getTitle())).append(" (").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append(" minutes), screened in room ").append(screenings.get(i).getRoom().getRoomName()).append(", at ").append(new SimpleDateFormat(dateFormat).format(screenings.get(i).getStartsDateTime()));
                } else {
                    result.append(StringUtils.capitalize(movie.getTitle())).append(" (").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append(" minutes), screened in room ").append(screenings.get(i).getRoom().getRoomName()).append(", at ").append(new SimpleDateFormat(dateFormat).format(screenings.get(i).getStartsDateTime())).append("\n");
                }
            }
            return result.toString();
        }
    }
}
