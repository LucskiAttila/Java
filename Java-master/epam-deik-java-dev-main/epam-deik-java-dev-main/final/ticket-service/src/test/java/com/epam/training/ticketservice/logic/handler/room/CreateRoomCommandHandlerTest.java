package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.CreateRoomCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomCommandHandlerTest {

    String ROOMNAME = "RoomName";
    String NUMBEROFROWSOFCHARIS = "25";
    String NUMBEROFCOLUMNSOFCHAIRS = "25";

    @Test
    public void testCreateRoomShouldReturnSuccessFullyCreateRoomWhenOk() {
        // Given
        CreateRoomCommand createRoomCommand = Mockito.mock(CreateRoomCommand.class);

        BDDMockito.given(createRoomCommand.operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("ok");

        CreateRoomCommandHandler underTest = new CreateRoomCommandHandler(createRoomCommand);

        // When
        String result = underTest.createRoom(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room is successfully created", result);
        Mockito.verify(createRoomCommand).operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testCreateRoomShouldReturnRoomAlreadyExistWhenExist() {
        // Given
        CreateRoomCommand createRoomCommand = Mockito.mock(CreateRoomCommand.class);

        BDDMockito.given(createRoomCommand.operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("exist");

        CreateRoomCommandHandler underTest = new CreateRoomCommandHandler(createRoomCommand);

        // When
        String result = underTest.createRoom(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room is already exists", result);
        Mockito.verify(createRoomCommand).operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testCreateRoomShouldReturnUserNotSignedInWhenSign() {
        // Given
        CreateRoomCommand createRoomCommand = Mockito.mock(CreateRoomCommand.class);

        BDDMockito.given(createRoomCommand.operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("sign");

        CreateRoomCommandHandler underTest = new CreateRoomCommandHandler(createRoomCommand);

        // When
        String result = underTest.createRoom(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(createRoomCommand).operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testCreateRoomShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        CreateRoomCommand createRoomCommand = Mockito.mock(CreateRoomCommand.class);

        BDDMockito.given(createRoomCommand.operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("admin");

        CreateRoomCommandHandler underTest = new CreateRoomCommandHandler(createRoomCommand);

        // When
        String result = underTest.createRoom(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(createRoomCommand).operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testCreateRoomShouldReturnInvalidNumberOfCharisWhenBadString() {
        // Given
        String badString = "S";
        CreateRoomCommand createRoomCommand = Mockito.mock(CreateRoomCommand.class);

        BDDMockito.given(createRoomCommand.operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn(badString);

        CreateRoomCommandHandler underTest = new CreateRoomCommandHandler(createRoomCommand);

        // When
        String result = underTest.createRoom(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You add invalid integer " + badString + " in number of chairs properties", result);
        Mockito.verify(createRoomCommand).operate(ROOMNAME, NUMBEROFROWSOFCHARIS, NUMBEROFCOLUMNSOFCHAIRS);
    }
}