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

class UpdateRoomCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs = "25";
        String numberOfColumnsOfChairs = "25";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);

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

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);

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

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
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

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnExistWhenRoomNotExist() {
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

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
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
    public void testOperateShouldReturnFirstWhenSecondParamCanBeUpdate() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="25";
        String numberOfColumnsOfChairs = "25";
        int ValidNumberOfColumnsOfChairs = 10;
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(ValidNumberOfColumnsOfChairs);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(Integer.parseInt(numberOfRowsOfChairs));
        BDDMockito.given(room.getComponents()).willReturn(Collections.emptyList());
        BDDMockito.given(room.getRoomName()).willReturn(roomName);

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("first", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(roomRepository).delete(room);
        Mockito.verify(roomRepository).save(new Room (roomName, Integer.parseInt(numberOfRowsOfChairs), Integer.parseInt(numberOfColumnsOfChairs), Collections.emptyList()));
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getComponents();
        Mockito.verify(room).getRoomName();
    }

    @Test
    public void testOperateShouldReturnSecondWhenFirstParamCanBeUpdate() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="25";
        String numberOfColumnsOfChairs = "25";
        int ValidNumberOfRowsOfChairs = 10;
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(Integer.parseInt(numberOfColumnsOfChairs));
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(ValidNumberOfRowsOfChairs);
        BDDMockito.given(room.getComponents()).willReturn(Collections.emptyList());
        BDDMockito.given(room.getRoomName()).willReturn(roomName);

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("second", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(roomRepository).delete(room);
        Mockito.verify(roomRepository).save(new Room (roomName, Integer.parseInt(numberOfRowsOfChairs), Integer.parseInt(numberOfColumnsOfChairs), Collections.emptyList()));
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getComponents();
        Mockito.verify(room).getRoomName();
    }

    @Test
    public void testOperateShouldReturnNothingWhenAllParamCanBeUpdate() {
        // Given
        String roomName = "RoomName";
        String numberOfRowsOfChairs ="25";
        String numberOfColumnsOfChairs = "25";
        int ValidNumberOfRowsOfChairs = 10;
        int ValidNumberOfColumnsOfChairs = 10;
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(ValidNumberOfColumnsOfChairs);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(ValidNumberOfRowsOfChairs);
        BDDMockito.given(room.getComponents()).willReturn(Collections.emptyList());
        BDDMockito.given(room.getRoomName()).willReturn(roomName);

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(roomRepository).delete(room);
        Mockito.verify(roomRepository).save(new Room (roomName, Integer.parseInt(numberOfRowsOfChairs), Integer.parseInt(numberOfColumnsOfChairs), Collections.emptyList()));
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getComponents();
        Mockito.verify(room).getRoomName();
    }

    @Test
    public void testOperateShouldReturnAllWhenNoParamCanBeUpdate() {
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
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(Integer.parseInt(numberOfColumnsOfChairs));
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(Integer.parseInt(numberOfRowsOfChairs));

        UpdateRoomCommand underTest = new UpdateRoomCommand(roomRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(roomName, numberOfRowsOfChairs, numberOfColumnsOfChairs);

        // Then
        assertEquals("all", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(room).getNumberOfRowsOfChairs();
    }
}
