package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AttachPriceComponentScreeningCommand {

    @Value("${ATTACH_MORE}")
    private boolean canAttachMore;

    @Value("${DATE_FORMAT_VALID}")
    String dateFormatValid;

    @Value("${DATE_FORMAT}")
    String dateFormat;

    private String permission_error;
    private String invalid_error;

    private Date startsDateTime_date;
    private Room room;
    private Movie movie;
    private Screening screening;
    private PriceComponent priceComponent;

    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public AttachPriceComponentScreeningCommand(RoomRepository roomRepository, MovieRepository movieRepository, ScreeningRepository screeningRepository, PriceComponentRepository priceComponentRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String title, String roomName, String startsDateTime) {
        if (hasPermission()) {
            if (checkDateFormat(startsDateTime)) {
                if (check(name, title, roomName)) {
                    formatDate(startsDateTime);
                    if (checkDateValid(startsDateTime)) {
                        if (isValid()) {
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

    private void save() {
        List<PriceComponent> components = screening.getComponents();
        components.add(priceComponent);
        screeningRepository.delete(screening);
        screeningRepository.save(new Screening(movie, room, startsDateTime_date, components));
    }

    private boolean isValid() {
        screening = screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateTime_date);
        return screening != null;
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

    private boolean isComponentValid(String name) {
        for (PriceComponent priceComponent : room.getComponents()) {
            if (name.equals(priceComponent.getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean check(String name, String title, String roomName) {
        invalid_error = "";
        movie = movieRepository.findByTitle(title);
        room =  roomRepository.findByRoomName(roomName);
        priceComponent = priceComponentRepository.findByName(name);
        if (movie == null) {
            invalid_error += "movie ";
        }
        if (room == null) {
            invalid_error += "room ";
        }
        if (priceComponent == null) {
            invalid_error += "component ";
        }
        return invalid_error.equals("");
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
