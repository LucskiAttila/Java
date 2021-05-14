package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;

import java.util.HashSet;
import java.util.Set;

public class UpdateMovieCommand extends AdminAbstractMovieCommand {

    private final String title;
    private final String genre;
    private final String durationInMinutes;

    private Movie movie;
    private Set<MovieProperties> changes;

    public UpdateMovieCommand (String title, String genre, String durationInMinutes) {
        this.title = title;
        this.genre = genre;
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    protected String operate() {
        if(isConvert()) {
            movie = getMovieByTitle();
            if (movie != null) {
                changes = getDifferent(movie);
                if (changes != null) {
                    getComponents();
                    update();
                    return updateTrue();
                } else {
                    return updateFalse();
                }
            } else {
                return deleteFalse();
            }
        } else {
            return invalidUpdate(getBad_string());
        }
    }

    private Set<MovieProperties> getDifferent(Movie movie) {
        Set<MovieProperties> result = new HashSet<>();
        if(movie.getGenre().equals(genre)) {
            result.add(MovieProperties.genre);
            if(movie.getDurationInMinutes().equals(durationInMinutes)) {
                result.add(MovieProperties.durationInMinutes);
            }
        }
        else if(movie.getDurationInMinutes().equals(durationInMinutes)) {
            result.add(MovieProperties.durationInMinutes);
        }
        return result;
    }
}
