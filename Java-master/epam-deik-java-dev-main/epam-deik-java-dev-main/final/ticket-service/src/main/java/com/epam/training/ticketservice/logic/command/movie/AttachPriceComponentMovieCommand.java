package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachPriceComponentMovieCommand {

    @Value("${ATTACH_MORE}")
    private boolean canAttachMore;

    private String permission_error;
    private String invalid_room;

    private Movie movie;
    private PriceComponent priceComponent;

    private final MovieRepository movieRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public AttachPriceComponentMovieCommand(MovieRepository movieRepository, PriceComponentRepository priceComponentRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String title) {
        if (hasPermission()) {
            if (isValid(name, title)) {
                boolean isDuplication = !isComponentValid(name);
                if (canAttachMore) {
                    save();
                    if (isDuplication) {
                        return "ok_duplicate";
                    } else {
                        return "ok";
                    }
                } else {
                    if (!isDuplication) {
                        save();
                        return "ok";
                    } else {
                        return "more";
                    }

                }
            } else {
                return invalid_room;
            }
        } else {
            return permission_error;
        }
    }

    private void save() {
        List<PriceComponent> components = movie.getComponents();
        components.add(priceComponent);
        movieRepository.delete(movie);
        movieRepository.save(new Movie(movie.getTitle(), movie.getGenre(), movie.getDurationInMinutes(), components));
    }

    private boolean isComponentValid(String name) {
        for (PriceComponent priceComponent : movie.getComponents()) {
            if (name.equals(priceComponent.getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(String name, String title) {
        movie = movieRepository.findByTitle(title);
        priceComponent = priceComponentRepository.findByName(name);
        if (movie == null) {
            invalid_room = "movie";
            if (priceComponent == null) {
                invalid_room = "all";
            }
            return false;
        }
        if (priceComponent == null) {
            invalid_room = "component";
            return false;
        }
        return true;
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
}
