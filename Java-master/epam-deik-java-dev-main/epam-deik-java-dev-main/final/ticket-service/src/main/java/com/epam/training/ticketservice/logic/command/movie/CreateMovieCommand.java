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
    private String permission_error;

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
                    int durationInMinutes_int = convertDurationInMinutes(durationInMinutes);
                    save(title, genre, durationInMinutes_int, new ArrayList<PriceComponent>());
                    return "ok";
                } else {
                    return "exist";
                }
            } else {
                return badString;
            }
        } else {
            return permission_error;
        }
    }

    private boolean hasPermission() {
        User user = userRepository.findByIsSigned(true);
        if (user != null) {
            if (user.getIsAdmin()) {
                return true;
            }
            else {
                permission_error = "admin";
                return false;
            }
        }
        else {
            permission_error = "sign";
            return false;
        }
    }

    private void save(String title, String genre, int durationInMinutes_int, ArrayList<PriceComponent> components) {
        movieRepository.save(new Movie(title, genre, durationInMinutes_int, components));
    }

    private boolean isValid(String title) {
        return movieRepository.findByTitle(title) != null;
    }

    private boolean isConvert(String durationInMinutes) {
        badString = validConvertToInt(durationInMinutes);
        return emptyString.equals(badString);
    }

    private String validConvertToInt(String number_str) {
        for (int i = 0; i < number_str.length(); i++) {
            if (!digits.contains(number_str.charAt(i))) {
                return String.valueOf(number_str.charAt(i));
            }
        }
        return emptyString;
    }

    private int convertDurationInMinutes(String durationInMinutes) {
        return Integer.parseInt(durationInMinutes);
    }
}

