package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateScreeningCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startsDateTime = "2012-12-12 12:12";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(screening);

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

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
    public void testOperateShouldReturnOverlapWhenScreeningWouldStartWhileAnotherScreeningOn() throws ParseException {
        // Given
        int time = 5;
        int listSize = 1;
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        List<Screening> list = Mockito.mock(List.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(room.getRoomName()).willReturn(roomName);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.isEmpty()).willReturn(false);
        BDDMockito.given(list.get(0)).willReturn(screening);
        BDDMockito.given(screening.getMovie()).willReturn(movie);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(time);
        BDDMockito.given(screening.getStartsDateTime()).willReturn(new SimpleDateFormat(dateFormat).parse("2012-12-12 12:12"));
        BDDMockito.given(screeningRepository.findByRoomAndStartsDateTimeBefore(room, new SimpleDateFormat(dateFormat).parse("2012-12-12 12:17"))).willReturn(list);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(null);


        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, StartsDateTime);

        // Then
        assertEquals("overlap", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(room).getRoomName();
        Mockito.verify(screeningRepository).findByRoomAndStartsDateTimeBefore(room, new SimpleDateFormat(dateFormat).parse("2012-12-12 12:17"));
        Mockito.verify(list).size();
        Mockito.verify(list, Mockito.times(2)).get(0);
        Mockito.verify(screening).getMovie();
        Mockito.verify(movie, Mockito.times(2)).getDurationInMinutes();
        Mockito.verify(screening).getStartsDateTime();
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
    }

    @Test
    public void testOperateShouldReturnBreakingWhenScreeningWouldStartWhileAnotherScreeningBreakOn() throws ParseException {
        // Given
        int breakTime = 10;
        int time = 5;
        int listSize = 1;
        String title = "Title";
        String roomName = "RoomName";
        String StartsDateTime = "2012-12-12 12:12";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        List<Screening> list = Mockito.mock(List.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(room.getRoomName()).willReturn(roomName);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.isEmpty()).willReturn(false);
        BDDMockito.given(list.get(0)).willReturn(screening);
        BDDMockito.given(screening.getMovie()).willReturn(movie);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(time);
        BDDMockito.given(screening.getStartsDateTime()).willReturn(new SimpleDateFormat(dateFormat).parse("2012-12-12 12:00"));
        BDDMockito.given(screeningRepository.findByRoomAndStartsDateTimeBefore(room, new SimpleDateFormat(dateFormat).parse("2012-12-12 12:17"))).willReturn(list);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(null);

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);
        underTest.setBreak_time(breakTime);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        String result = underTest.operate(title, roomName, StartsDateTime);

        // Then
        assertEquals("breaking", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(room).getRoomName();
        Mockito.verify(screeningRepository).findByRoomAndStartsDateTimeBefore(room, new SimpleDateFormat(dateFormat).parse("2012-12-12 12:17"));
        Mockito.verify(list).size();
        Mockito.verify(list, Mockito.times(2)).get(0);
        Mockito.verify(screening).getMovie();
        Mockito.verify(movie, Mockito.times(2)).getDurationInMinutes();
        Mockito.verify(screening).getStartsDateTime();
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
    }

    @Test
    public void testOperateShouldReturnOkWhenScreeningCanBeCreated() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String invalidRoomName = "roomName";
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

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(room.getRoomName()).willReturn(invalidRoomName);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime))).willReturn(null);

        CreateScreeningCommand underTest = new CreateScreeningCommand(screeningRepository, movieRepository, roomRepository, userRepository);
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
        Mockito.verify(room).getRoomName();
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime));
        Mockito.verify(screeningRepository).save(new Screening(movie, room, new SimpleDateFormat(dateFormat).parse(StartsDateTime), Collections.emptyList()));
    }
}
