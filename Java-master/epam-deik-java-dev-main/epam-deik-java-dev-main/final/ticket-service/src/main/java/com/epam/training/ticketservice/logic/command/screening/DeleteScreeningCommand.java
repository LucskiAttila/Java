package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DeleteScreeningCommand {

    @Value("${DATE_FORMAT_VALID}")
    String dateFormatValid;

    @Value("${DATE_FORMAT}")
    String dateFormat;

    private Movie movie;
    private Room room;
    private Screening screening;

    private String invalid_error;
    private String permission_error;

    private Date startsDateTime_date;

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public DeleteScreeningCommand(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public String operate(String title, String roomName, String startsDateAndTime) {
        if (hasPermission()) {
            if (checkDateFormat(startsDateAndTime)) {
                if (check(title, roomName)) {
                    formatDate(startsDateAndTime);
                    if (isValid()) {
                        delete();
                        return "ok";
                    } else {
                        return "exist";
                    }
                } else {
                    return invalid_error;
                }
            } else {
                return "format";
            }
        } else {
            return permission_error;
        }
    }

    private void formatDate(String startsDateAndTime) {
        try {
            startsDateTime_date = new SimpleDateFormat(dateFormat).parse(startsDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        screeningRepository.delete(screening);
    }

    private boolean check(String title, String roomName) {
        room = roomRepository.findByRoomName(roomName);
        movie = movieRepository.findByTitle(title);
        invalid_error = "";
        if (room == null) {
            invalid_error += roomName;
            if (movie == null) {
                invalid_error += " " + title;
            }
            return false;
        } else if (movie == null) {
            invalid_error += " " + title;
            return false;
        }
        return true;
    }

    private boolean checkDateFormat(String startsDateAndTime) {
        return startsDateAndTime.matches(dateFormatValid);
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

    private boolean isValid() {
        screening = screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateTime_date);
        return screening != null;
    }
}
