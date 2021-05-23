package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.AttachPriceComponentMovieCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class AttachPriceComponentMovieCommandHandlerTest {

    String NAME = "Name";
    String TITLE = "Title";

    @Test
    public void testAttachComponentToMovieShouldReturnSuccessFullyCreateAttachWhenEmpty() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("ok");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is successfully attached to " + TITLE, result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnMovieNotExistWhenFirst() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("first");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie doesn't exists", result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnComponentNotExistWhenSecond() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("second");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " price component doesn't exists", result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnMovieAndComponentNotExistWhenAll() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("all");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " movie, " + NAME + " price component doesn't exists", result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnComponentAlreadyAttachedWhenMore() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("more");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is already attached to " + TITLE, result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnComponentAttachedAgainWhenOkDuplicate() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("okDuplicate");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is successfully attached again to " + TITLE, result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnUserNotSignedInWhenSign() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("sign");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }

    @Test
    public void testAttachComponentToMovieShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        AttachPriceComponentMovieCommand attachPriceComponentMovieCommand = Mockito.mock(AttachPriceComponentMovieCommand.class);

        BDDMockito.given(attachPriceComponentMovieCommand.operate(NAME, TITLE)).willReturn("admin");

        AttachPriceComponentMovieCommandHandler underTest = new AttachPriceComponentMovieCommandHandler(attachPriceComponentMovieCommand);

        // When
        String result = underTest.attachPriceComponentMovie(NAME, TITLE);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(attachPriceComponentMovieCommand).operate(NAME, TITLE);
    }
}
