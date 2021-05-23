package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.DeleteRoomCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class DeleteRoomCommandHandlerTest {

    String ROOMNAME = "roomName";

    @Test
    public void testDeleteRoomShouldReturnSuccessFullyCreateMovieWhenOk() {
        // Given
        DeleteRoomCommand deleteRoomCommand = Mockito.mock(DeleteRoomCommand.class);

        BDDMockito.given(deleteRoomCommand.operate(ROOMNAME)).willReturn("ok");

        DeleteRoomCommandHandler underTest = new DeleteRoomCommandHandler(deleteRoomCommand);

        // When
        String result = underTest.deleteRoom(ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room is successfully deleted", result);
        Mockito.verify(deleteRoomCommand).operate(ROOMNAME);
    }

    @Test
    public void testDeleteRoomShouldReturnRoomNotExistWhenExist() {
        // Given
        DeleteRoomCommand deleteRoomCommand = Mockito.mock(DeleteRoomCommand.class);

        BDDMockito.given(deleteRoomCommand.operate(ROOMNAME)).willReturn("exist");

        DeleteRoomCommandHandler underTest = new DeleteRoomCommandHandler(deleteRoomCommand);

        // When
        String result = underTest.deleteRoom(ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room doesn't exists", result);
        Mockito.verify(deleteRoomCommand).operate(ROOMNAME);
    }

    @Test
    public void testDeleteRoomShouldReturnUserNotSignedInWhenSign() {
        // Given
        DeleteRoomCommand deleteRoomCommand = Mockito.mock(DeleteRoomCommand.class);

        BDDMockito.given(deleteRoomCommand.operate(ROOMNAME)).willReturn("sign");

        DeleteRoomCommandHandler underTest = new DeleteRoomCommandHandler(deleteRoomCommand);

        // When
        String result = underTest.deleteRoom(ROOMNAME);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(deleteRoomCommand).operate(ROOMNAME);
    }

    @Test
    public void testDeleteRoomShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        DeleteRoomCommand deleteRoomCommand = Mockito.mock(DeleteRoomCommand.class);

        BDDMockito.given(deleteRoomCommand.operate(ROOMNAME)).willReturn("admin");

        DeleteRoomCommandHandler underTest = new DeleteRoomCommandHandler(deleteRoomCommand);

        // When
        String result = underTest.deleteRoom(ROOMNAME);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(deleteRoomCommand).operate(ROOMNAME);
    }
}