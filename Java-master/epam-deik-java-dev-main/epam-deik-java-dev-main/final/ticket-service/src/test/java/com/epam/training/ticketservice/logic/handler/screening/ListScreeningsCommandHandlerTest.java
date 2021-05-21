package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.logic.command.screening.ListScreeningsCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListScreeningsCommandHandlerTest {

    @Test
    public void testListScreeningShouldReturnNoScreeningsWhenScreeningListEmpty() {
        // Given
        ListScreeningsCommand listScreeningsCommand = Mockito.mock(ListScreeningsCommand.class);

        BDDMockito.given(listScreeningsCommand.operate()).willReturn(Collections.emptyList());

        ListScreeningsCommandHandler underTest = new ListScreeningsCommandHandler(listScreeningsCommand);

        // When
        String result = underTest.listScreenings();

        // Then
        assertEquals("There are no screenings", result);
        Mockito.verify(listScreeningsCommand).operate();
    }

    @Test
    public void testListScreeningShouldReturnListOfScreeningsWhenScreeningListNotEmpty() throws ParseException {
        // Given
        int listSize = 2;
        String dateFormat = "yyyy-MM-dd HH:mm";
        String date = "2012-12-12 12:12";
        String title = "Title";
        String roomName = "RoomName";
        String genre = "Genre";
        int durationTime = 15;
        ListScreeningsCommand listScreeningsCommand = Mockito.mock(ListScreeningsCommand.class);

        List<Screening> list = Mockito.mock(List.class);
        Screening screening = Mockito.mock(Screening.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(listScreeningsCommand.operate()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(screening);
        BDDMockito.given(list.get(1)).willReturn(screening);
        BDDMockito.given((screening.getMovie())).willReturn(movie);
        BDDMockito.given(screening.getRoom()).willReturn(room);
        BDDMockito.given(screening.getStartsDateTime()).willReturn(new SimpleDateFormat(dateFormat).parse(date));
        BDDMockito.given(room.getRoomName()).willReturn(roomName);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(durationTime);
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getTitle()).willReturn(title);

        ListScreeningsCommandHandler underTest = new ListScreeningsCommandHandler(listScreeningsCommand);
        underTest.setDateFormat(dateFormat);

        // When
        String result = underTest.listScreenings();

        // Then
        String result_helper = StringUtils.capitalize(title) + " (" + genre + ", " + durationTime + " minutes), screened in room " + roomName + ", at " + date;
        assertEquals(result_helper + "\n" + result_helper, result);
        Mockito.verify(listScreeningsCommand).operate();
        Mockito.verify(list, Mockito.times(5)).size();
        Mockito.verify(list, Mockito.times(3)).get(0);
        Mockito.verify(list, Mockito.times(3)).get(1);
        Mockito.verify(screening, Mockito.times(2)).getMovie();
        Mockito.verify(screening, Mockito.times(2)).getRoom();
        Mockito.verify(screening, Mockito.times(2)).getStartsDateTime();
        Mockito.verify(room, Mockito.times(2)).getRoomName();
        Mockito.verify(movie, Mockito.times(2)).getDurationInMinutes();
        Mockito.verify(movie, Mockito.times(2)).getGenre();
        Mockito.verify(movie, Mockito.times(2)).getTitle();
    }
}