package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListMoviesCommandTest {

    @Test
    public void testOperateShouldReturnMovieListWhenCalling() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

        ListMoviesCommand underTest = new ListMoviesCommand(movieRepository);

        BDDMockito.given(movieRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Movie> result = underTest.operate();

        // Then
        assertEquals(Collections.emptyList(), result);
        Mockito.verify(movieRepository).findAll();
    }
}
