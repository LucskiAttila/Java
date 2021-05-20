package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, ScreeningId> {
    //@Query(value = "SELECT * FROM SCREENING Screening WHERE Screening.movie = :movie and Screening.room = :room and Screening.startTimeAndTime = :startsDateAndTime", nativeQuery = true)
    //Screening findByPrimaryKey(@Param("movie") Movie movie, @Param("room") Room room, @Param("startsDateAndTime") Date startsDateAndTime);

    //@Query(value = "SELECT * FROM Screening WHERE Screening.room = ?1 and Screening.startDateAndTime < ?2", nativeQuery = true)
    //List<Screening> findCollision(Room room, Date endsDateAndTime);

    Screening findByMovieAndRoomAndStartsDateTime(Movie movie, Room room, Date startsDateTime);

    List<Screening> findByRoomAndStartsDateTimeBefore(Room room, Date endsDateAndTime);
}
