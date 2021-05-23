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

    private List<PriceComponent> components;

    private String permissionError;
    private String invalidRoom;

    private Movie movie;
    private PriceComponent priceComponent;

    public void setCanAttachMore(boolean canAttachMore) {
        this.canAttachMore = canAttachMore;
    }

    private final MovieRepository movieRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public AttachPriceComponentMovieCommand(MovieRepository movieRepository,
                                            PriceComponentRepository priceComponentRepository,
                                            UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String title) {
        if (hasPermission()) {
            if (isValid(name, title)) {
                boolean isDuplication = isComponentValid(name);
                if (canAttachMore) {
                    save(title);
                    if (isDuplication) {
                        return "okDuplicate";
                    } else {
                        return "ok";
                    }
                } else {
                    if (!isDuplication) {
                        save(title);
                        return "ok";
                    } else {
                        return "more";
                    }

                }
            } else {
                return invalidRoom;
            }
        } else {
            return permissionError;
        }
    }

    private void save(String title) {
        components.add(priceComponent);
        movieRepository.delete(movie);
        movieRepository.save(new Movie(title, movie.getGenre(), movie.getDurationInMinutes(), components));
    }

    private boolean isComponentValid(String name) {
        components = movie.getComponents();
        int size = components.size();
        for (int i = 0; i < size; i++) {
            if (name.equals(components.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(String name, String title) {
        movie = movieRepository.findByTitle(title);
        priceComponent = priceComponentRepository.findByName(name);
        if (movie == null) {
            invalidRoom = "first";
            if (priceComponent == null) {
                invalidRoom = "all";
            }
            return false;
        }
        if (priceComponent == null) {
            invalidRoom = "second";
            return false;
        }
        return true;
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
}
