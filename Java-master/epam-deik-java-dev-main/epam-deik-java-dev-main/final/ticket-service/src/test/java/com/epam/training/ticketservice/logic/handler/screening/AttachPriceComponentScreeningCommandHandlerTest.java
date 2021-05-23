package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.AttachPriceComponentScreeningCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class AttachPriceComponentScreeningCommandHandlerTest {

    String NAME = "Name";
    String TITLE = "Title";
    String ROOMNAME = "Roomname";
    String DATETIME = "2012-12-12 12:12";

    @Test
    public void testAttachComponentToScreeningShouldReturnSuccessFullyCreateAttachWhenEmpty() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("ok");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is successfully attached to " + TITLE + " " + ROOMNAME + " " + DATETIME, result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnMovieNotExistWhenMovie() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("movie ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + "movie doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnRoomNotExistWhenRoom() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("room ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + "room doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnComponentNotExistWhenComponent() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("component ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + "price component doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnMovieAndRoomExistWhenMovieRoom() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("movie room ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(TITLE) + "movie, " + ROOMNAME + "room doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnRoomAndComponentExistWhenRoomComponent() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("room component ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(ROOMNAME) + "room, " + NAME + "price component doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnMovieAndComponentExistWhenMovieComponent() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("movie component ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(TITLE) + "movie, " + NAME + "price component doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnMovieAndRoomAndComponentExistWhenMovieRoomComponent() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("movie room component ");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(TITLE) + "movie, " + ROOMNAME + "room, " + NAME + "price component doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnComponentAlreadyAttachedWhenMore() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("more");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(NAME) + " component is already attached to " + TITLE + " " + ROOMNAME + " " + DATETIME, result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnComponentAttachedAgainWhenOkDuplicate() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("okDuplicate");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(NAME) + " component is successfully attached again to " + TITLE + " " + ROOMNAME + " " + DATETIME, result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnUserNotSignedInWhenSign() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("sign");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( "You aren't signed in", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("admin");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( "You don't have permission", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnScreeningNotExistWhenExist() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("exist");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( StringUtils.capitalize(TITLE) + ", " + ROOMNAME + ", " + DATETIME + "screening doesn't exists", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnInvalidDateFormatWhenFormat() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("format");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( "You use invalid dateformat", result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testAttachComponentToScreeningShouldReturnInvalidDateWhenInvalid() {
        // Given
        AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand= Mockito.mock(AttachPriceComponentScreeningCommand.class);

        BDDMockito.given(attachPriceComponentScreeningCommand.operate(NAME, TITLE, ROOMNAME, DATETIME)).willReturn("invalid");

        AttachPriceComponentScreeningCommandHandler underTest = new AttachPriceComponentScreeningCommandHandler(attachPriceComponentScreeningCommand);

        // When
        String result = underTest.attachPriceComponentScreening(NAME, TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals( "You add invalid date: " + DATETIME, result);
        Mockito.verify(attachPriceComponentScreeningCommand).operate(NAME, TITLE, ROOMNAME, DATETIME);
    }
}
