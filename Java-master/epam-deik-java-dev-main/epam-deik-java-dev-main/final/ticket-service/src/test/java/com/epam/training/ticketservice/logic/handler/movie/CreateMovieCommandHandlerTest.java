package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.CreateMovieCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class CreateMovieCommandHandlerTest {

    String TITLE = "Title";
    String GENRE = "Genre";
    String DURATIONINMINUTES = "10";

    @Test
    public void testCreateMovieShouldReturnSuccessFullyCreateMovieWhenOk() {
        // Given
        CreateMovieCommand createMovieCommand = Mockito.mock(CreateMovieCommand.class);

        BDDMockito.given(createMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("ok");

        CreateMovieCommandHandler underTest = new CreateMovieCommandHandler(createMovieCommand);

        // When
        String result = underTest.createMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie is successfully created", result);
        Mockito.verify(createMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testCreateMovieShouldReturnMovieAlreadyExistWhenExist() {
        // Given
        CreateMovieCommand createMovieCommand = Mockito.mock(CreateMovieCommand.class);

        BDDMockito.given(createMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("exist");

        CreateMovieCommandHandler underTest = new CreateMovieCommandHandler(createMovieCommand);

        // When
        String result = underTest.createMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie is already exists", result);
        Mockito.verify(createMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testCreateMovieShouldReturnUserNotSignedInWhenSign() {
        // Given
        CreateMovieCommand createMovieCommand = Mockito.mock(CreateMovieCommand.class);

        BDDMockito.given(createMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("sign");

        CreateMovieCommandHandler underTest = new CreateMovieCommandHandler(createMovieCommand);

        // When
        String result = underTest.createMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(createMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testCreateMovieShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        CreateMovieCommand createMovieCommand = Mockito.mock(CreateMovieCommand.class);

        BDDMockito.given(createMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("admin");

        CreateMovieCommandHandler underTest = new CreateMovieCommandHandler(createMovieCommand);

        // When
        String result = underTest.createMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(createMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testCreateMovieShouldReturnInvalidDurationInMinutesWhenBadString() {
        // Given
        String badString = "S";
        CreateMovieCommand createMovieCommand = Mockito.mock(CreateMovieCommand.class);

        BDDMockito.given(createMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn(badString);

        CreateMovieCommandHandler underTest = new CreateMovieCommandHandler(createMovieCommand);

        // When
        String result = underTest.createMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You add invalid integer " + badString + " in " + DURATIONINMINUTES, result);
        Mockito.verify(createMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }
}
