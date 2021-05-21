package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.DeleteScreeningCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class DeleteScreeningCommandHandlerTest {

    String TITLE = "Title";
    String ROOMNAME = "Roomname";
    String DATETIME = "2012-12-12 12:12";

    @Test
    public void testDeleteScreeningShouldReturnSuccessFullyDeleteScreeningInWhenOk() {
        // Given
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("ok");

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " " +StringUtils.capitalize(ROOMNAME) + " " + DATETIME  + " screening is successfully deleted", result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldReturnNotExistScreeningInWhenExist() {
        // Given
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("exist");

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " " +StringUtils.capitalize(ROOMNAME) + " " + DATETIME  + " screening doesn't exists", result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldUserNotSignedInWhenSign() {
        // Given
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("sign");

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldUserNotAdminInWhenAdmin() {
        // Given
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("admin");

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldInvalidDateFormatWhenFormat() {
        // Given
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("format");

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You use invalid dateformat", result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldInvalidDateWhenInvalid() {
        // Given
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("invalid");

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid date: " + DATETIME, result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldInvalidPropertiesWhenRoomName() {
        // Given
        String prop = ROOMNAME;
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn(prop);

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid properties: " + prop, result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldInvalidPropertiesWhenTitle() {
        // Given
        String prop = TITLE;
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn(prop);

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid properties: " + prop, result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testDeleteScreeningShouldInvalidPropertiesWhenRoomNameAndTitle() {
        // Given
        String prop = ROOMNAME + " " + TITLE;
        DeleteScreeningCommand deleteScreeningCommand = Mockito.mock(DeleteScreeningCommand.class);

        BDDMockito.given(deleteScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn(prop);

        DeleteScreeningCommandHandler underTest = new DeleteScreeningCommandHandler(deleteScreeningCommand);

        // When
        String result = underTest.deleteScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid properties: " + prop, result);
        Mockito.verify(deleteScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }
}