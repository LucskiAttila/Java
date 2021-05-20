package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CreateScreeningCommand {

    @Value("${DATE_FORMAT_VALID}")
    String dateFormatValid;

    @Value("${DATE_FORMAT}")
    String dateFormat;

    @Value("${BREAKING_TIME}")
    int break_time;

    private Movie movie;
    private Room room;

    private String invalid_error;
    private String permission_error;
    private String badDate;

    private Date startsDateTime_date;

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public CreateScreeningCommand(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository, UserRepository userRepository) {
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
                            if (checkError(roomName)) {
                                screeningRepository.save(new Screening(movie, room, startsDateTime_date, new ArrayList<PriceComponent>()));
                                return "ok";
                            } else {
                                return badDate;
                            }
                        } else {
                            return "exist";
                        }
                    } else {
                        return "invalid";
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

    private boolean checkDateValid(String startsDateTime) {
        return startsDateTime.equals(new SimpleDateFormat(dateFormat).format(startsDateTime_date));
    }

    private void formatDate(String startsDateAndTime) {
        try {
            startsDateTime_date = new SimpleDateFormat(dateFormat).parse(startsDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean checkError(String roomName) {
        if (!roomName.equals(room.getRoomName())) {
            return true;
        }
        int movie_length = movie.getDurationInMinutes();
        Calendar c = Calendar.getInstance();
        c.setTime(startsDateTime_date);
        c.add(Calendar.MINUTE, movie_length);
        Date endDate = c.getTime();
        List<Screening> screeningList = screeningRepository.findByRoomAndStartsDateTimeBefore(room, endDate);
        for (Screening screening_actual: screeningList) {
            int movie_length_actual = screening_actual.getMovie().getDurationInMinutes();
            Calendar c_actual = Calendar.getInstance();
            c_actual.setTime(screening_actual.getStartsDateTime());
            c_actual.add(Calendar.MINUTE, movie_length_actual);
            Date endDate_actual = c_actual.getTime();
            if(endDate_actual.getTime() >= startsDateTime_date.getTime()) {
                badDate = "overlap";
                return false;
            }
            Calendar c_actual_break = Calendar.getInstance();
            c_actual_break.setTime(endDate_actual);
            c_actual_break.add(Calendar.MINUTE, break_time);
            Date endDate_actual_break = c_actual_break.getTime();
            if(endDate_actual.getTime() < startsDateTime_date.getTime() && endDate_actual_break.getTime() >= startsDateTime_date.getTime()) {
                badDate = "breaking";
                return false;
            }
        }
        return true;
    }

    private boolean isValid() {
        return screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateTime_date) == null;
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
}
