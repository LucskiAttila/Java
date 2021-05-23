package com.epam.training.ticketservice.logic.handler.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.logic.command.room.ListRoomsCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRoomsCommandHandlerTest {

    @Test
    public void testListRoomsShouldReturnNoRoomsWhenRoomsListEmpty() {
        // Given
        ListRoomsCommand listRoomsCommand = Mockito.mock(ListRoomsCommand.class);

        BDDMockito.given(listRoomsCommand.operate()).willReturn(Collections.emptyList());

        ListRoomsCommandHandler underTest = new ListRoomsCommandHandler(listRoomsCommand);

        // When
        String result = underTest.listRooms();

        // Then
        assertEquals("There are no rooms at the moment", result);
        Mockito.verify(listRoomsCommand).operate();
    }

    @Test
    public void testListRoomsShouldReturnListOfRoomsWhenRoomsListNotEmpty() {
        // Given
        int listSize = 2;
        String roomName = "RoomName";
        int numberOfRowsOfChairs = 25;
        int numberOfColumnsOfChairs = 25;
        ListRoomsCommand listRoomsCommand = Mockito.mock(ListRoomsCommand.class);

        List<Room> list = Mockito.mock(List.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(listRoomsCommand.operate()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(room);
        BDDMockito.given(list.get(1)).willReturn(room);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(room.getRoomName()).willReturn(roomName);

        ListRoomsCommandHandler underTest = new ListRoomsCommandHandler(listRoomsCommand);

        // When
        String result = underTest.listRooms();

        // Then
        String result_helper = StringUtils.capitalize("room ") + roomName + " with " + numberOfRowsOfChairs * numberOfColumnsOfChairs + " seats, " + numberOfRowsOfChairs + " rows and " + numberOfColumnsOfChairs + " columns";
        assertEquals(result_helper + "\n" + result_helper, result);
        Mockito.verify(listRoomsCommand).operate();
        Mockito.verify(list, Mockito.times(5)).size();
        Mockito.verify(list, Mockito.times(4)).get(0);
        Mockito.verify(list, Mockito.times(4)).get(1);
        Mockito.verify(room, Mockito.times(4)).getNumberOfRowsOfChairs();
        Mockito.verify(room, Mockito.times(4)).getNumberOfColumnsOfChairs();
        Mockito.verify(room, Mockito.times(2)).getRoomName();
    }
}
