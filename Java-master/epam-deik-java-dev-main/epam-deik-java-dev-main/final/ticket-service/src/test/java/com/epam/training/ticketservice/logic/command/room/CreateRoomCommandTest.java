package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs = "25";
        String numberOfColumnsOfChairs = "25";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        CreateRoomCommand underTest = new CreateRoomCommand(userRepository, roomRepository);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs = "25";
        String numberOfColumnsOfChairs = "25";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        CreateRoomCommand underTest = new CreateRoomCommand(userRepository, roomRepository);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnBadStringWhenNumberOfRowsOfChairsIsInvalid() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="2A";
        String numberOfColumnsOfChairs = "25";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        CreateRoomCommand underTest = new CreateRoomCommand(userRepository, roomRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnBadStringWhenNumberOfColumnsOfChairsIsInvalid() {
        // Given
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="25";
        String numberOfColumnsOfChairs = "2A";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        CreateRoomCommand underTest = new CreateRoomCommand(userRepository, roomRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnExistWhenRoomAlreadyExist() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="25";
        String numberOfColumnsOfChairs = "25";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);

        CreateRoomCommand underTest = new CreateRoomCommand(userRepository, roomRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
    }

    @Test
    public void testOperateShouldReturnOkWhenRoomCanBeCreate() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="25";
        String numberOfColumnsOfChairs = "25";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);

        CreateRoomCommand underTest = new CreateRoomCommand(userRepository, roomRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(roomRepository).save(new Room (roomName, Integer.parseInt(numberOfRowsOfChairs), Integer.parseInt(numberOfColumnsOfChairs), Collections.emptyList()));
    }
}