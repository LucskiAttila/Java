package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.logic.command.room.AttachPriceComponentRoomCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class AttachPriceComponentRoomCommandHandlerTest {

    String NAME = "Name";
    String ROOMNAME = "RoomNAme";

    @Test
    public void testAttachComponentToRoomShouldReturnSuccessFullyCreateAttachWhenEmpty() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("ok");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is successfully attached to " + ROOMNAME, result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnRoomNotExistWhenFirst() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("first");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room doesn't exists", result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnComponentNotExistWhenSecond() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("second");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " price component doesn't exists", result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnRoomAndComponentNotExistWhenAll() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("all");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(ROOMNAME) + " room, " + NAME + " price component doesn't exists", result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnComponentAlreadyAttachedWhenMore() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("more");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is already attached to " + ROOMNAME, result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnComponentAttachedAgainWhenOkDuplicate() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("okDuplicate");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " component is successfully attached again to " + ROOMNAME, result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnUserNotSignedInWhenSign() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("sign");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }

    @Test
    public void testAttachComponentToRoomShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        AttachPriceComponentRoomCommand attachPriceComponentRoomCommand= Mockito.mock(AttachPriceComponentRoomCommand.class);

        BDDMockito.given(attachPriceComponentRoomCommand.operate(NAME, ROOMNAME)).willReturn("admin");

        AttachPriceComponentRoomCommandHandler underTest = new AttachPriceComponentRoomCommandHandler(attachPriceComponentRoomCommand);

        // When
        String result = underTest.attachPriceComponentRoom(NAME, ROOMNAME);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(attachPriceComponentRoomCommand).operate(NAME, ROOMNAME);
    }
}
