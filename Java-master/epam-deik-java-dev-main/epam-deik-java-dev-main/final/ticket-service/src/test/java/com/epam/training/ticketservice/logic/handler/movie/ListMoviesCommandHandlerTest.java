package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.logic.command.movie.ListMoviesCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListMoviesCommandHandlerTest {

    @Test
    public void testListMoviesShouldReturnNoMoviesWhenMoviesListEmpty() {
        // Given
        ListMoviesCommand listMoviesCommand = Mockito.mock(ListMoviesCommand.class);

        BDDMockito.given(listMoviesCommand.operate()).willReturn(Collections.emptyList());

        ListMoviesCommandHandler underTest = new ListMoviesCommandHandler(listMoviesCommand);

        // When
        String result = underTest.listMovies();

        // Then
        assertEquals("There are no movies at the moment", result);
        Mockito.verify(listMoviesCommand).operate();
    }

    @Test
    public void testListMoviesShouldReturnListOfMoviesWhenMoviesListNotEmpty() {
        // Given
        int listSize = 2;
        String title = "Title";
        String genre = "Genre";
        int durationTime = 15;
        ListMoviesCommand listMoviesCommand = Mockito.mock(ListMoviesCommand.class);

        List<Movie> list = Mockito.mock(List.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(listMoviesCommand.operate()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(movie);
        BDDMockito.given(list.get(1)).willReturn(movie);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(durationTime);
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getTitle()).willReturn(title);

        ListMoviesCommandHandler underTest = new ListMoviesCommandHandler(listMoviesCommand);

        // When
        String result = underTest.listMovies();

        // Then
        String result_helper = StringUtils.capitalize(title) + " (" + genre + ", " + durationTime + " minutes)";
        assertEquals(result_helper + "\n" + result_helper, result);
        Mockito.verify(listMoviesCommand).operate();
        Mockito.verify(list, Mockito.times(5)).size();
        Mockito.verify(list, Mockito.times(3)).get(0);
        Mockito.verify(list, Mockito.times(3)).get(1);
        Mockito.verify(movie, Mockito.times(2)).getDurationInMinutes();
        Mockito.verify(movie, Mockito.times(2)).getGenre();
        Mockito.verify(movie, Mockito.times(2)).getTitle();
    }
}
