package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.UpdateRoomCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class UpdateRoomCommandHandlerTest {

    String ROOMNAME = "roomName";
    String NUMBEROFROWOFCHAIRS = "25";
    String NUMBEROFCOLUMNSOFCHAIRS = "25";

    @Test
    public void testUpdateRoomShouldReturnSuccessFullyUpdateRoomWhenEmptyString() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You successfully updated all properties of " + ROOMNAME, result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnSuccessFullyUpdateRoomNumberOfColumnsOfChairWhenFirst() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("first");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You successfully updated number of columns of charis of " + ROOMNAME, result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnSuccessFullyUpdateRoomNumberOfRowsOfCharisWhenSecond() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("second");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You successfully updated number of rows of charis of " + ROOMNAME, result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnSamePropertiesWhenAll() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("all");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You add same properties", result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnRoomNotExistWhenExist() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("exist");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room doesn't exists", result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnUserNotSignedInWhenSign() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("sign");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn("admin");

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }

    @Test
    public void testUpdateRoomShouldReturnInvalidNumberOfChairsWhenBadString() {
        // Given
        String badString = "S";
        UpdateRoomCommand updateRoomCommand = Mockito.mock(UpdateRoomCommand.class);

        BDDMockito.given(updateRoomCommand.operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS)).willReturn(badString);

        UpdateRoomCommandHandler underTest = new UpdateRoomCommandHandler(updateRoomCommand);

        // When
        String result = underTest.updateRoom(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);

        // Then
        assertEquals("You add invalid integer " + badString + " in number of chairs properties", result);
        Mockito.verify(updateRoomCommand).operate(ROOMNAME, NUMBEROFROWOFCHAIRS, NUMBEROFCOLUMNSOFCHAIRS);
    }
}
