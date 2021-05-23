package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class DeleteScreeningCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        String result = underTest.operate(title, roomName, startsDateTime);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        String result = underTest.operate(title, roomName, startsDateTime);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnFormatWhenDateInWrongFormat() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String InvalidDateFormatValid = "([0-9]{4})([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(InvalidDateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, startsDateTime);

        // Then
        assertEquals("format", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnRoomNameWhenRoomNotExist() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, startsDateTime);

        // Then
        assertEquals(roomName, result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnMovieTitleWhenMovieNotExist() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, startsDateTime);

        // Then
        assertEquals(title, result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnRoomNameAndMovieTitleWhenRoomNameAndMovieNotExist() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, startsDateTime);

        // Then
        assertEquals(roomName + " " + title, result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnInvalidWhenDateIsInvalid() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String InvalidStartsDateTime = "2012-22-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, InvalidStartsDateTime);

        // Then
        assertEquals("invalid", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnExistWhenScreeningNotValid() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(null);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, StartsDateTime);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
    }

    @Test
    public void testOperateShouldReturnOkWhenScreeningCanBeDeleted() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(screening);

        DeleteScreeningCommand underTest = new DeleteScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, StartsDateTime);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(screeningRepository).delete(screening);
    }
}