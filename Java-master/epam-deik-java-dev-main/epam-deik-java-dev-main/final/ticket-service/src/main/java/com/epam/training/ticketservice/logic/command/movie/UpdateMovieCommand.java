package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateMovieCommand {

    private String permissionError;
    private String badString;

    private Movie movie;

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    private final String emptyString = "";

    @Value("#{'${DIGITS}'.split(',')}")
    List<Character> digits;

    public void setDigits(List<Character> digits) {
        this.digits = digits;
    }

    public UpdateMovieCommand(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public String operate(String title, String genre, String durationInMinutes) {
        if (hasPermission()) {
            if (isConvert(durationInMinutes)) {
                if (isValid(title)) {
                    int durationInMinutesFormatInt = convertDurationInMinutes(durationInMinutes);
                    String matching = getNotDifferent(movie, genre, durationInMinutesFormatInt);
                    if (!"all".equals(matching)) {
                        update(genre, durationInMinutesFormatInt);
                    }
                    return matching;
                } else {
                    return "exist";
                }
            } else {
                return badString;
            }
        } else {
            return permissionError;
        }
    }

    private boolean hasPermission() {
        User user = userRepository.findByIsSigned(true);
        if (user != null) {
            if (user.getIsAdmin()) {
                return true;
            } else {
                permissionError = "admin";
                return false;
            }
        } else {
            permissionError = "sign";
            return false;
        }
    }

    private void update(String genre, int durationInMinutes) {
        movieRepository.delete(movie);
        movieRepository.save(new Movie(movie.getTitle(), genre, durationInMinutes, movie.getComponents()));
    }

    private boolean isValid(String title) {
        movie = movieRepository.findByTitle(title);
        return movie != null;
    }

    private boolean isConvert(String durationInMinutes) {
        badString = validConvertToInt(durationInMinutes);
        return emptyString.equals(badString);
    }

    private String validConvertToInt(String numberFormatStr) {
        for (int i = 0; i < numberFormatStr.length(); i++) {
            if (!digits.contains(numberFormatStr.charAt(i))) {
                return String.valueOf(numberFormatStr.charAt(i));
            }
        }
        return emptyString;
    }

    protected int convertDurationInMinutes(String durationInMinutes) {
        return Integer.parseInt(durationInMinutes);
    }

    private String getNotDifferent(Movie movie, String genre, int durationInMinutes) {
        String result = emptyString;
        if (movie.getGenre().equals(genre)) {
            result = "first";
            if (movie.getDurationInMinutes() == durationInMinutes) {
                result = "all";
            }
        } else if (movie.getDurationInMinutes() == durationInMinutes) {
            result = "second";
        }
        return result;
    }
}
