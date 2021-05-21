package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteMovieCommand {

    private String permission_error;
    private Movie movie;

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public DeleteMovieCommand(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public String operate(String title) {
        if (hasPermission()) {
            if (isValid(title)) {
                delete();
                return "ok";
            } else {
                return "exist";
            }
        } else {
            return permission_error;
        }
    }

    private void delete() {
        movieRepository.delete(movie);
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

    private boolean isValid(String title) {
        movie = movieRepository.findByTitle(title);
        return movie != null;
    }
}
