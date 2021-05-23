package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DeleteRoomCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String roomName = "roomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        DeleteRoomCommand underTest = new DeleteRoomCommand(roomRepository, userRepository);

        // When
        String result = underTest.operate(roomName);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String roomName = "roomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        DeleteRoomCommand underTest = new DeleteRoomCommand(roomRepository, userRepository);

        // When
        String result = underTest.operate(roomName);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnExistWhenRoomNotExist() {
        // Given
        String roomName = "roomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);

        DeleteRoomCommand underTest = new DeleteRoomCommand(roomRepository, userRepository);

        // When
        String result = underTest.operate(roomName);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
    }

    @Test
    public void testOperateShouldReturnOkWhenRoomCanBeCreate() {
        // Given
        String roomName = "roomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);

        DeleteRoomCommand underTest = new DeleteRoomCommand(roomRepository, userRepository);

        // When
        String result = underTest.operate(roomName);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(roomRepository).delete(room);
    }
}
