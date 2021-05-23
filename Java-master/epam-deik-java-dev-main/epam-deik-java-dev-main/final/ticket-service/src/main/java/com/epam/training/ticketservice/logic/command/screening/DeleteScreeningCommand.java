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

@Component
public class DeleteScreeningCommand {

    @Value("${DATE_FORMAT_VALID}")
    String dateFormatValid;

    public void setDateFormatValid(String dateFormatValid) {
        this.dateFormatValid = dateFormatValid;
    }

    @Value("${DATE_FORMAT}")
    String dateFormat;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private Movie movie;
    private Room room;
    private Screening screening;

    private String invalidError;
    private String permissionError;

    private Date startsDateTimeFormatDate;

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public DeleteScreeningCommand(ScreeningRepository screeningRepository,
                                  MovieRepository movieRepository,
                                  RoomRepository roomRepository,
                                  UserRepository userRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public String operate(String title, String roomName, String startsDateTime) {
        if (hasPermission()) {
            if (checkDateFormat(startsDateTime)) {
                if (check(title, roomName)) {
                    formatDate(startsDateTime);
                    if (checkDateValid(startsDateTime)) {
                        if (isValid()) {
                            delete();
                            return "ok";
                        } else {
                            return "exist";
                        }
                    } else {
                        return "invalid";
                    }
                } else {
                    return invalidError;
                }
            } else {
                return "format";
            }
        } else {
            return permissionError;
        }
    }

    private boolean checkDateValid(String startsDateTime) {
        return startsDateTime.equals(new SimpleDateFormat(dateFormat).format(startsDateTimeFormatDate));
    }

    private void formatDate(String startsDateAndTime) {
        try {
            startsDateTimeFormatDate = new SimpleDateFormat(dateFormat).parse(startsDateAndTime);
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
        invalidError = "";
        if (room == null) {
            invalidError += roomName;
            if (movie == null) {
                invalidError += " " + title;
            }
            return false;
        } else if (movie == null) {
            invalidError += title;
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
            } else {
                permissionError = "admin";
                return false;
            }
        } else {
            permissionError = "sign";
            return false;
        }
    }

    private boolean isValid() {
        screening = screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateTimeFormatDate);
        return screening != null;
    }
}
