package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttachPriceComponentScreeningCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnFormatWhenDateInWrongFormat() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String InvalidDateFormatValid = "([0-9]{4})([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(InvalidDateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("format", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnRoomWhenRoomNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("room ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnMovieWhenMovieNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("movie ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnComponentWhenPriceComponentNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("component ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnRoomAndMovieWhenRoomAndMovieNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("movie room ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnRoomAndComponentWhenRoomAndPriceComponentNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("room component ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnMovieAndComponentWhenMovieAndPriceComponentNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("movie component ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnMovieAndRoomAndComponentWhenMovieAndRoomAndPriceComponentNotExist() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, startsDateTime);

        // Then
        assertEquals("movie room component ", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnInvalidWhenDateIsInvalid() {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String InvalidStartsDateTime = "2012-22-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, InvalidStartsDateTime);

        // Then
        assertEquals("invalid", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnExistWhenScreeningNotExist() throws ParseException {
        // Given
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(name, title, roomName, StartsDateTime);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnMoreWhenAlreadyAttachedAndNotValidDuplication() throws ParseException {
        // Given
        int listSize = 1;
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        Screening screening = Mockito.mock(Screening.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(screening);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(screening.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(name);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setCanAttachMore(false);
        String result = underTest.operate(name, title, roomName, StartsDateTime);

        // Then
        assertEquals("more", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(screening).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
    }

    @Test
    public void testOperateShouldReturnOkWhenAttachedNotExistAndNotValidDuplication() throws ParseException {
        // Given
        int listSize = 1;
        String name = "Name";
        String OtherName = "OtherName";
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        Screening screening = Mockito.mock(Screening.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(screening);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(screening.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(OtherName);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setCanAttachMore(false);
        String result = underTest.operate(name, title, roomName, StartsDateTime);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(screening).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(screeningRepository).delete(screening);
        Mockito.verify(screeningRepository).save(new Screening(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime), list));
    }

    @Test
    public void testOperateShouldReturnOkWhenAttachedNotExistAndValidDuplication() throws ParseException {
        // Given
        int listSize = 1;
        String name = "Name";
        String OtherName = "OtherName";
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        Screening screening = Mockito.mock(Screening.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(screening);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(screening.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(OtherName);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setCanAttachMore(true);
        String result = underTest.operate(name, title, roomName, StartsDateTime);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(screening).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(screeningRepository).delete(screening);
        Mockito.verify(screeningRepository).save(new Screening(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime), list));
    }

    @Test
    public void testOperateShouldReturnOkDuplicateWhenAttachedNotExistAndValidDuplication() throws ParseException {
        // Given
        int listSize = 1;
        String name = "Name";
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        Screening screening = Mockito.mock(Screening.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(screening);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(screening.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(name);

        AttachPriceComponentScreeningCommand underTest = new AttachPriceComponentScreeningCommand(roomRepository, movieRepository, screeningRepository, priceComponentRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setCanAttachMore(true);
        String result = underTest.operate(name, title, roomName, StartsDateTime);

        // Then
        assertEquals("okDuplicate", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(screening).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(screeningRepository).delete(screening);
        Mockito.verify(screeningRepository).save(new Screening(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime), list));
    }
}
