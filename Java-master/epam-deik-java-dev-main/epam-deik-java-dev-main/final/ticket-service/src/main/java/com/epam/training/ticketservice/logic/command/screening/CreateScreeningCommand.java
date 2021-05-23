package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
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

    public void setDateFormatValid(String dateFormatValid) {
        this.dateFormatValid = dateFormatValid;
    }

    @Value("${DATE_FORMAT}")
    String dateFormat;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Value("${BREAKING_TIME}")
    int breakTime;

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }

    private Movie movie;
    private Room room;

    private String invalidError;
    private String permissionError;
    private String badDate;

    private Date startsDateTimeFormatDate;

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public CreateScreeningCommand(ScreeningRepository screeningRepository,
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
                            if (checkError(roomName)) {
                                screeningRepository.save(new Screening(movie, room, startsDateTimeFormatDate,
                                        new ArrayList<PriceComponent>()));
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

    private boolean checkError(String roomName) {
        if (!roomName.equals(room.getRoomName())) {
            return true;
        }
        int movieLength = movie.getDurationInMinutes();
        Calendar c = Calendar.getInstance();
        c.setTime(startsDateTimeFormatDate);
        c.add(Calendar.MINUTE, movieLength);
        Date endDate = c.getTime();
        List<Screening> screeningList = screeningRepository.findByRoomAndStartsDateTimeBefore(room, endDate);
        for (int i = 0; i < screeningList.size(); i++) {
            int movieLengthActual = screeningList.get(i).getMovie().getDurationInMinutes();
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.setTime(screeningList.get(i).getStartsDateTime());
            calendarActual.add(Calendar.MINUTE, movieLengthActual);
            Date endDateActual = calendarActual.getTime();
            if (endDateActual.getTime() >= startsDateTimeFormatDate.getTime()) {
                badDate = "overlap";
                return false;
            }
            Calendar calendarActualBreak = Calendar.getInstance();
            calendarActualBreak.setTime(endDateActual);
            calendarActualBreak.add(Calendar.MINUTE, breakTime);
            Date endDateActualBreak = calendarActualBreak.getTime();
            if (endDateActual.getTime() < startsDateTimeFormatDate.getTime()
                    && endDateActualBreak.getTime() >= startsDateTimeFormatDate.getTime()) {
                badDate = "breaking";
                return false;
            }
        }
        return true;
    }

    private boolean isValid() {
        return screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateTimeFormatDate) == null;
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

    private boolean checkDateFormat(String startsDateTime) {
        return startsDateTime.matches(dateFormatValid);
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
