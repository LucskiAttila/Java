package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ScreeningRepository extends CrudRepository <Screening, String> {
    @Query("value = SELECT * FROM Screening WHERE Screening.title = :title and Screening.roomName = :roomName and Screening.startTimeAndTime = :startsDateAndTime", nativeQuery = true)
    Screening findByPrimaryKey(@Param("title") Movie title, @Param("roomName") Room roomName, @Param("startsDateAndTime") Date startsDateAndTime);

    @Query("SELECT * FROM Screening WHERE and Screening.roomName = ?1 and Screening.startDateAndTime < 2?")
    List<Screening> findCollision(Room roomName, Date endsDateAndTime);
}
