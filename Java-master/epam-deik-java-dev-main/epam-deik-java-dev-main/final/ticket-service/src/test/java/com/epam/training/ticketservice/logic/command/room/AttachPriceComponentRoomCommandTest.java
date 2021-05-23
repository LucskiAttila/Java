package com.epam.training.ticketservice.logic.command.room;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttachPriceComponentRoomCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnFirstWhenRoomParameterInValid() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("first", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
    }

    @Test
    public void testOperateShouldReturnSecondWhenPriceComponentParameterInValid() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("second", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnAllWhenPriceRoomAndComponentParameterInValid() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("all", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnMoreWhenAlreadyAttachedAndNotValidDuplication() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        int listSize = 1;
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(room.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(name);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);
        underTest.setCanAttachMore(false);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("more", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(room).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
    }

    @Test
    public void testOperateShouldReturnOkWhenAttachedNotExistAndNotValidDuplication() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        String OtherName = "OtherName";
        int numberOfRowsOfCharis = 25;
        int numberOfColumnsOfChairs = 25;
        int listSize = 1;
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(room.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(OtherName);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfCharis);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);
        underTest.setCanAttachMore(false);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(room).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(roomRepository).delete(room);
        Mockito.verify(roomRepository).save(new Room(roomName, numberOfRowsOfCharis, numberOfColumnsOfChairs, list));
    }

    @Test
    public void testOperateShouldReturnOkWhenAttachedNotExistAndValidDuplication() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        String OtherName = "OtherName";
        int numberOfRowsOfCharis = 25;
        int numberOfColumnsOfChairs = 25;
        int listSize = 1;
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(room.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(OtherName);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfCharis);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);
        underTest.setCanAttachMore(true);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(room).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(roomRepository).delete(room);
        Mockito.verify(roomRepository).save(new Room(roomName, numberOfRowsOfCharis, numberOfColumnsOfChairs, list));
    }

    @Test
    public void testOperateShouldReturnOkDuplicateWhenAttachedNotExistAndValidDuplication() {
        // Given
        String name = "Name";
        String roomName = "RoomName";
        String OtherName = "OtherName";
        int numberOfRowsOfCharis = 25;
        int numberOfColumnsOfChairs = 25;
        int listSize = 1;
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(room.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(name);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfCharis);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);

        AttachPriceComponentRoomCommand underTest = new AttachPriceComponentRoomCommand(roomRepository, userRepository, priceComponentRepository);
        underTest.setCanAttachMore(true);

        // When
        String result = underTest.operate(name, roomName);

        // Then
        assertEquals("okDuplicate", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(room).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(roomRepository).delete(room);
        Mockito.verify(roomRepository).save(new Room(roomName, numberOfRowsOfCharis, numberOfColumnsOfChairs, list));
    }
}
