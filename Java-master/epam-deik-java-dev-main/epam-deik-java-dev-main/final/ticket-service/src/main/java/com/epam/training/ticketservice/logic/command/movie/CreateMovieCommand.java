package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateMovieCommand {

    private String badString;
    private String permissionError;

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    private final String emptyString = "";

    @Value("#{'${DIGITS}'.split(',')}")
    List<Character> digits;

    public void setDigits(List<Character> digits) {
        this.digits = digits;
    }

    public CreateMovieCommand(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public String operate(String title, String genre, String durationInMinutes) {
        if (hasPermission()) {
            if (isConvert(durationInMinutes)) {
                if (!isValid(title)) {
                    int durationInMinutesFormatInt = convertDurationInMinutes(durationInMinutes);
                    save(title, genre, durationInMinutesFormatInt, new ArrayList<PriceComponent>());
                    return "ok";
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

    private void save(String title, String genre,
                      int durationInMinutesFormatInt,
                      ArrayList<PriceComponent> components) {
        movieRepository.save(new Movie(title, genre, durationInMinutesFormatInt, components));
    }

    private boolean isValid(String title) {
        return movieRepository.findByTitle(title) != null;
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

    private int convertDurationInMinutes(String durationInMinutes) {
        return Integer.parseInt(durationInMinutes);
    }
}

