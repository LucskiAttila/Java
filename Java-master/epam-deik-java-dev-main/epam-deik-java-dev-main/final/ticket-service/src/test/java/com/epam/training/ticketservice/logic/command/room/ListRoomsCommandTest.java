package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRoomsCommandTest {

    @Test
    public void testOperateShouldReturnRoomListWhenCalling() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);

        ListRoomsCommand underTest = new ListRoomsCommand(roomRepository);

        BDDMockito.given(roomRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Room> result = underTest.operate();

        // Then
        assertEquals(Collections.emptyList(), result);
        Mockito.verify(roomRepository).findAll();
    }

}