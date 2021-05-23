package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.UpdateMovieCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class UpdateMovieCommandHandlerTest {

    String TITLE = "Title";
    String GENRE = "Genre";
    String DURATIONINMINUTES = "10";

    @Test
    public void testUpdateMovieShouldReturnSuccessFullyUpdateMovieWhenEmptyString() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You successfully updated all properties of " + TITLE, result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnSuccessFullyUpdateMovieDurationInMinutesWhenFirst() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("first");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You successfully updated duration in minutes of " + TITLE, result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnSuccessFullyUpdateMovieGenreWhenSecond() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("second");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You successfully updated genre of " + TITLE, result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnSamePropertiesWhenAll() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("all");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You add same properties", result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnMovieNotExistWhenExist() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("exist");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie doesn't exists", result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnUserNotSignedInWhenSign() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("sign");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn("admin");

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }

    @Test
    public void testUpdateMovieShouldReturnInvalidDurationInMinutesWhenABadString() {
        // Given
        String badString = "S";
        UpdateMovieCommand updateMovieCommand = Mockito.mock(UpdateMovieCommand.class);

        BDDMockito.given(updateMovieCommand.operate(TITLE, GENRE, DURATIONINMINUTES)).willReturn(badString);

        UpdateMovieCommandHandler underTest = new UpdateMovieCommandHandler(updateMovieCommand);

        // When
        String result = underTest.updateMovie(TITLE, GENRE, DURATIONINMINUTES);

        // Then
        assertEquals("You add invalid integer " + badString + " in " + DURATIONINMINUTES, result);
        Mockito.verify(updateMovieCommand).operate(TITLE, GENRE, DURATIONINMINUTES);
    }
}
