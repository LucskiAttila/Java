package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, ScreeningId> {
    Screening findByMovieAndRoomAndStartsDateTime(Movie movie, Room room, Date startsDateTime);

    List<Screening> findByRoomAndStartsDateTimeBefore(Room room, Date endsDateAndTime);
}
