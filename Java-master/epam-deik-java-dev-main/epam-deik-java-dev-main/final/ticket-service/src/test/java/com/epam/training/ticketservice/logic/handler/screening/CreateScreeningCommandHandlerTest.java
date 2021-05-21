package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.CreateScreeningCommand;
import com.epam.training.ticketservice.logic.command.user.SignInPrivilegedCommand;
import com.epam.training.ticketservice.logic.handler.user.SignInPrivilegedCommandHandler;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class CreateScreeningCommandHandlerTest {

    String TITLE = "Title";
    String ROOMNAME = "Roomname";
    String DATETIME = "2012-12-12 12:12";

    @Test
    public void testCreateScreeningShouldReturnSuccessFullyCreateScreeningInWhenOk() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("ok");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " " +StringUtils.capitalize(ROOMNAME) + " " + DATETIME  + " screening is successfully created", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnScreeningAlreadyExistWhenExist() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("exist");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals(StringUtils.capitalize(TITLE) + " " +StringUtils.capitalize(ROOMNAME) + " " + DATETIME  + " screening is already exists", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnAlreadySignInWhenSign() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("sign");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnSignInNotAdminWhenSign() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("admin");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnOverlappingScreeningWhenOverlap() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("overlap");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("There is an overlapping screening", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnBreakingWhileScreeningWhenBreaking() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("breaking");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("This would start in the break period after another screening in this room", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnInvalidDateWhenInvalid() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("invalid");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid date: " + DATETIME, result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testSCreateScreeningUserShouldReturnInvalidPropertiesWhenRoom() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("room");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid properties: " + "room", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnInvalidPropertiesWhenTitle() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("title");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid properties: " + "title", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }

    @Test
    public void testCreateScreeningShouldReturnInvalidPropertiesWhenRoomAndTitle() {
        // Given
        CreateScreeningCommand createScreeningCommand = Mockito.mock(CreateScreeningCommand.class);

        BDDMockito.given(createScreeningCommand.operate(TITLE, ROOMNAME, DATETIME)).willReturn("room title");

        CreateScreeningCommandHandler underTest = new CreateScreeningCommandHandler(createScreeningCommand);

        // When
        String result = underTest.createScreening(TITLE, ROOMNAME, DATETIME);

        // Then
        assertEquals("You add invalid properties: " + "room title", result);
        Mockito.verify(createScreeningCommand).operate(TITLE, ROOMNAME, DATETIME);
    }
}
