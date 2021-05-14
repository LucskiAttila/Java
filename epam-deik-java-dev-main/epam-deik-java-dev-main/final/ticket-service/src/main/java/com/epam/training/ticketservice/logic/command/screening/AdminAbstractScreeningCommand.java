package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.logic.command.AdminAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AdminAbstractScreeningCommand extends AdminAbstract {

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    PriceComponentRepository priceComponentRepository;

    @Autowired
    String title;

    @Autowired
    String roomName;

    @Autowired
    String startsDateAndTime;

    @Autowired
    Set<ScreeningProperties> changes;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    String name;

    @Autowired
    @Value("${DATE_FORMAT}")
    String dateFormat;

    @Autowired
    @Value("${BREAKING_TIME}")
    int break_time;

    private Screening screening;

    private List<PriceComponent> components;

    private Room room;

    private Movie movie;

    private Date startsDateAndTime_date;

    private PriceComponent priceComponent;

    private String invalidError;

    private String badDate;

    private String type = "screening";

    protected boolean setProperties() {
        components = room.getComponents();
        if (!components.contains(priceComponent)) {
            movie = screening.getMovie();
            room = screening.getRoom();
            startsDateAndTime_date = screening.getStartsDateAndTime();
            components.add(priceComponent);
            return true;
        } else {
            return false;
        }
    }

    protected String getBad_date() {
        return badDate;
    }

    protected boolean checkError() {
        int movie_length = movie.getDurationInMinutes();
        Calendar c = Calendar.getInstance();
        c.setTime(startsDateAndTime_date);
        c.add(Calendar.MINUTE, movie_length);
        Date endDate = c.getTime();
        List<Screening> screeningList = screeningRepository.findCollision(room, endDate);
        for (Screening screening_actual: screeningList) {
            int movie_length_actual = screening_actual.getMovie().getDurationInMinutes();
            Calendar c_actual = Calendar.getInstance();
            c_actual.setTime(screening_actual.getStartsDateAndTime());
            c_actual.add(Calendar.MINUTE, movie_length_actual);
            Date endDate_actual = c_actual.getTime();
            if(endDate_actual.getTime() > startsDateAndTime_date.getTime()) {
                badDate += ScreeningProperties.overlap.toString();
                return false;
            }
            Calendar c_actual_break = Calendar.getInstance();
            c_actual_break.setTime(endDate_actual);
            c_actual_break.add(Calendar.MINUTE, break_time);
            Date endDate_actual_break = c_actual_break.getTime();
            if(endDate_actual.getTime() < startsDateAndTime_date.getTime() && endDate_actual_break.getTime() > startsDateAndTime_date.getTime()) {
                badDate += ScreeningProperties.breaking.toString();
                return false;
            }
        }
        return true;
    }

    protected void formatDate() {
        try {
            startsDateAndTime_date = new SimpleDateFormat(dateFormat).parse(startsDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void setComponents() {
        components = new ArrayList<PriceComponent>();
    }

    protected boolean check() {
        room = roomRepository.findByroomName(roomName);
        movie = movieRepository.findBytitle(title);
        if (room == null) {
            invalidError += ScreeningProperties.roomName.toString();
            if (movie == null) {
                invalidError += " " + ScreeningProperties.title.toString();
            }
            return false;
        } else if (movie == null) {
            invalidError += " " + ScreeningProperties.title.toString();
            return false;
        }
        return true;
    }

    protected boolean isValid_Attachment() {
        priceComponent = priceComponentRepository.findByName(name);
        screening = screeningRepository.findByPrimaryKey(movie, room, startsDateAndTime_date);
        if (priceComponent == null) {
            invalidError += " " + ScreeningProperties.price_component.toString();
            if(screening == null) {
                invalidError += " " + ScreeningProperties.screening.toString();
            }
            return false;
        } else if (screening == null) {
            invalidError += " " + ScreeningProperties.screening.toString();
        }
        return true;
    }

    protected String getInValidError() {
        return invalidError;
    }

    protected boolean isValid() {
        return screeningRepository.findByPrimaryKey(movie, room, startsDateAndTime_date) != null;
    }

    protected boolean checkDateFormat() {
        return startsDateAndTime.matches(dateFormat);
    }

    protected Room getRoomByName() {
        return roomRepository.findByroomName(roomName);
    }

    protected void delete() {
        roomRepository.delete(roomName);
    }

    protected void save() {
        roomRepository.save(new Screening(movie, room, startsDateAndTime_date, components));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected String modifiedProperties() {
        StringBuilder result = new StringBuilder();
        for (ScreeningProperties change : changes) {
            result.append(capitalize((change.toString()))).append(" ");
        }
        return result.toString();
    }
}
