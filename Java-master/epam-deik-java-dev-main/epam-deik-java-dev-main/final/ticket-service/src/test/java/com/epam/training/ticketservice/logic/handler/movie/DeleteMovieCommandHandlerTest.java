package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.CreateMovieCommand;
import com.epam.training.ticketservice.logic.command.movie.DeleteMovieCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class DeleteMovieCommandHandlerTest {

    String TITLE = "Title";

    @Test
    public void testDeleteMovieShouldReturnSuccessFullyCreateMovieWhenOk() {
        // Given
        DeleteMovieCommand deleteMovieCommand = Mockito.mock(DeleteMovieCommand.class);

        BDDMockito.given(deleteMovieCommand.operate(TITLE)).willReturn("ok");

        DeleteMovieCommandHandler underTest = new DeleteMovieCommandHandler(deleteMovieCommand);

        // When
        String result = underTest.deleteMovie(TITLE);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie is successfully deleted", result);
        Mockito.verify(deleteMovieCommand).operate(TITLE);
    }

    @Test
    public void testDeleteMovieShouldReturnMovieAlreadyExistWhenExist() {
        // Given
        DeleteMovieCommand deleteMovieCommand = Mockito.mock(DeleteMovieCommand.class);

        BDDMockito.given(deleteMovieCommand.operate(TITLE)).willReturn("exist");

        DeleteMovieCommandHandler underTest = new DeleteMovieCommandHandler(deleteMovieCommand);

        // When
        String result = underTest.deleteMovie(TITLE);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie doesn't exists", result);
        Mockito.verify(deleteMovieCommand).operate(TITLE);
    }

    @Test
    public void testDeleteMovieShouldReturnUserNotSignedInWhenSign() {
        // Given
        DeleteMovieCommand deleteMovieCommand = Mockito.mock(DeleteMovieCommand.class);

        BDDMockito.given(deleteMovieCommand.operate(TITLE)).willReturn("sign");

        DeleteMovieCommandHandler underTest = new DeleteMovieCommandHandler(deleteMovieCommand);

        // When
        String result = underTest.deleteMovie(TITLE);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(deleteMovieCommand).operate(TITLE);
    }

    @Test
    public void testDeleteMovieShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        DeleteMovieCommand deleteMovieCommand = Mockito.mock(DeleteMovieCommand.class);

        BDDMockito.given(deleteMovieCommand.operate(TITLE)).willReturn("admin");

        DeleteMovieCommandHandler underTest = new DeleteMovieCommandHandler(deleteMovieCommand);

        // When
        String result = underTest.deleteMovie(TITLE);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(deleteMovieCommand).operate(TITLE);
    }
}
