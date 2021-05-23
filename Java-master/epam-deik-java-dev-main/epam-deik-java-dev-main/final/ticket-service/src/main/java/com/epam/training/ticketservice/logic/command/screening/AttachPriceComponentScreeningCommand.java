package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AttachPriceComponentScreeningCommand {

    @Value("${ATTACH_MORE}")
    private boolean canAttachMore;

    List<PriceComponent> components;

    @Value("${DATE_FORMAT_VALID}")
    String dateFormatValid;

    public void setCanAttachMore(boolean canAttachMore) {
        this.canAttachMore = canAttachMore;
    }

    @Value("${DATE_FORMAT}")
    String dateFormat;

    public void setDateFormatValid(String dateFormatValid) {
        this.dateFormatValid = dateFormatValid;
    }

    private String permissionError;
    private String invalidError;

    private Date startsDateTimeFormatDate;
    private Room room;
    private Movie movie;
    private Screening screening;
    private PriceComponent priceComponent;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public AttachPriceComponentScreeningCommand(RoomRepository roomRepository,
                                                MovieRepository movieRepository,
                                                ScreeningRepository screeningRepository,
                                                PriceComponentRepository priceComponentRepository,
                                                UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String operate(String name, String title, String roomName, String startsDateTime) {
        if (hasPermission()) {
            if (checkDateFormat(startsDateTime)) {
                if (check(name, title, roomName)) {
                    formatDate(startsDateTime);
                    if (checkDateValid(startsDateTime)) {
                        if (isValid()) {
                            boolean isDuplication = isComponentValid(name);
                            if (canAttachMore) {
                                save();
                                if (isDuplication) {
                                    return "okDuplicate";
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

    private void save() {
        components.add(priceComponent);
        screeningRepository.delete(screening);
        screeningRepository.save(new Screening(movie, room, startsDateTimeFormatDate, components));
    }

    private boolean isValid() {
        screening = screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateTimeFormatDate);
        return screening != null;
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

    private boolean isComponentValid(String name) {
        components = screening.getComponents();
        int size = components.size();
        for (int i = 0; i < size; i++) {
            if (name.equals(components.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean check(String name, String title, String roomName) {
        invalidError = "";
        movie = movieRepository.findByTitle(title);
        room =  roomRepository.findByRoomName(roomName);
        priceComponent = priceComponentRepository.findByName(name);
        if (movie == null) {
            invalidError += "movie ";
        }
        if (room == null) {
            invalidError += "room ";
        }
        if (priceComponent == null) {
            invalidError += "component ";
        }
        return invalidError.equals("");
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
}
