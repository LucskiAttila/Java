package com.epam.training.ticketservice.logic.command.book;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, true);

        // Then
        assertEquals(List.of("sign"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInAdmin() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";
        String userName = "admin";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(user.getUserName()).willReturn(userName);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, true);

        // Then
        assertEquals(List.of("admin", userName), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(user).getUserName();
    }

    @Test
    public void testOperateShouldReturnMovieWhenMovieNotExist() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, true);

        // Then
        assertEquals(List.of("movie"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnRoomWhenRoomNotExist() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(null);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, true);

        // Then
        assertEquals(List.of("room"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
    }

    @Test
    public void testOperateShouldReturnFormatWhenDateInWrongFormat() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String InvalidDateFormatValid = "([0-9]{4})([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(InvalidDateFormatValid);
        underTest.setDateFormat(dateFormat);
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, true);

        // Then
        assertEquals(List.of("format"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
    }

    @Test
    public void testOperateShouldReturnDateWhenRoomNotExist() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String InvalidStartDateTime = "2012-22-12 12:12";
        String seats = "1,1 2,2";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        List<Object> result = underTest.operate(title, roomName, InvalidStartDateTime, seats, true);

        // Then
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        assertEquals(List.of("date", simpleDateFormat.format(simpleDateFormat.parse(InvalidStartDateTime))), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
    }

    @Test
    public void testOperateShouldReturnScreeningWhenDateInWrongFormat() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(null);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, true);

        // Then
        assertEquals(List.of("screening"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
    }

    @Test
    public void testOperateShouldReturnBadStringWhenSetsRowOrColumnNumberNotValid() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 2,A";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of("A", 6), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
    }

    @Test
    public void testOperateShouldReturnCommaWhenTooMuchCommasUsed() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1, 2,2";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of(",", 3), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
    }

    @Test
    public void testOperateShouldReturnRowWhenNotEnoughRowNumber() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 ,2";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of("row"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
    }

    @Test
    public void testOperateShouldReturnColumnWhenNotEnoughColumnNumber() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 1,";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of("column"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
    }

    @Test
    public void testOperateShouldReturnExistWhenInvalidRowOrColumnNumber() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 6,1";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of("exist", new Seat(6, 1)), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
    }

    @Test
    public void testOperateShouldReturnTakenWhenRowColumnNumberAlreadyUsed() throws ParseException {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 5,1";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;
        int listSeatSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);
        List<Seat> listSeat = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(listSeat);
        BDDMockito.given(listSeat.size()).willReturn(listSeatSize);
        BDDMockito.given(listSeat.get(0)).willReturn(new Seat(5, 1));

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of("taken", new Seat(5, 1)), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
        Mockito.verify(listSeat).size();
        Mockito.verify(listSeat).get(0);
    }

    @Test
    public void testOperateShouldReturnBookWhenookSuccessfullyCreated() throws ParseException {
        // Given
        String userName = "UserName";
        String password = "Password";
        int listComponentSize = 1;
        int componentPrice = 1000;
        int basPrice = 2000;
        String currency = "HUF";
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 5,1";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);
        List<BasePrice> listBasePrice = Mockito.mock(List.class);
        List<PriceComponent> listComponentPrice = Mockito.mock(List.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        BasePrice basePrice = Mockito.mock(BasePrice.class);
        List<Book> listBooks = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());
        BDDMockito.given(basePriceRepository.findAll()).willReturn(listBasePrice);
        BDDMockito.given(listBasePrice.get(0)).willReturn(basePrice);
        BDDMockito.given(basePrice.getBasePriceValue()).willReturn(basPrice);
        BDDMockito.given(room.getComponents()).willReturn(listComponentPrice);
        BDDMockito.given(listComponentPrice.size()).willReturn(listComponentSize);
        BDDMockito.given(listComponentPrice.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getPrice()).willReturn(componentPrice);
        BDDMockito.given(movie.getComponents()).willReturn(listComponentPrice);
        BDDMockito.given(screening.getComponents()).willReturn(listComponentPrice);
        BDDMockito.given(user.getPassword()).willReturn(password);
        BDDMockito.given(user.getBook()).willReturn(listBooks);
        BDDMockito.given(user.getUserName()).willReturn(userName);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        underTest.setCurrency(currency);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, true);

        // Then
        assertEquals(List.of("book", List.of(new Seat(1, 1), new Seat(5, 1)), 10000, "HUF"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
        Mockito.verify(basePriceRepository).findAll();
        Mockito.verify(listBasePrice).get(0);
        Mockito.verify(basePrice).getBasePriceValue();
        Mockito.verify(room).getComponents();
        Mockito.verify(listComponentPrice, Mockito.times(3)).size();
        Mockito.verify(listComponentPrice, Mockito.times(3)).get(0);
        Mockito.verify(priceComponent, Mockito.times(3)).getPrice();
        Mockito.verify(movie).getComponents();
        Mockito.verify(screening).getComponents();
        Mockito.verify(bookRepository).save(new Book(screening, List.of(new Seat(1, 1), new Seat(5, 1)), 10000));
        Mockito.verify(user).getPassword();
        Mockito.verify(user).getBook();
        Mockito.verify(listBooks).add(new Book(screening, List.of(new Seat(1, 1), new Seat(5, 1)), 10000));
        Mockito.verify(user).getUserName();
        Mockito.verify(userRepository).delete(user);
        Mockito.verify(userRepository).save(new User(userName, password, false, true, listBooks));
    }

    @Test
    public void testOperateShouldReturnMovieWhenUserNotSignedIn() {
        // Given
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String seats = "1,1 2,2";

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByIsAdmin(true)).willReturn(user);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        List<Object> result = underTest.operate(title, roomName, startDateTime, seats, false);

        // Then
        assertEquals(List.of("movie"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(userRepository).findByIsAdmin(true);
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnShowWhenInvalidShowPriceForBooking() throws ParseException {
        // Given
        int listComponentSize = 1;
        int componentPrice = 1000;
        int basPrice = 2000;
        String currency = "HUF";
        String title = "Title";
        String roomName = "RoomName";
        String startDateTime = "2012-12-12 12:12";
        String InValidSeats = "1,1 5,1";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String DateFormatValid = "([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})";
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        int numberOfRowsOfChairs = 5;
        int numberOfColumnsOfChairs = 5;
        int listSize = 1;

        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);
        Screening screening = Mockito.mock(Screening.class);
        Book book = Mockito.mock(Book.class);
        List<Book> list = Mockito.mock(List.class);
        List<BasePrice> listBasePrice = Mockito.mock(List.class);
        List<PriceComponent> listComponentPrice = Mockito.mock(List.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        BasePrice basePrice = Mockito.mock(BasePrice.class);
        List<Book> listBooks = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(roomRepository.findByRoomName(roomName)).willReturn(room);
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime))).willReturn(screening);
        BDDMockito.given(room.getNumberOfRowsOfChairs()).willReturn(numberOfRowsOfChairs);
        BDDMockito.given(room.getNumberOfColumnsOfChairs()).willReturn(numberOfColumnsOfChairs);
        BDDMockito.given(bookRepository.findByScreening(screening)).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(book);
        BDDMockito.given(book.getSeats()).willReturn(Collections.emptyList());
        BDDMockito.given(basePriceRepository.findAll()).willReturn(listBasePrice);
        BDDMockito.given(listBasePrice.get(0)).willReturn(basePrice);
        BDDMockito.given(basePrice.getBasePriceValue()).willReturn(basPrice);
        BDDMockito.given(room.getComponents()).willReturn(listComponentPrice);
        BDDMockito.given(listComponentPrice.size()).willReturn(listComponentSize);
        BDDMockito.given(listComponentPrice.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getPrice()).willReturn(componentPrice);
        BDDMockito.given(movie.getComponents()).willReturn(listComponentPrice);
        BDDMockito.given(screening.getComponents()).willReturn(listComponentPrice);

        BookCommand underTest = new BookCommand(bookRepository, screeningRepository, roomRepository, movieRepository, userRepository, basePriceRepository);

        // When
        underTest.setDateFormatValid(DateFormatValid);
        underTest.setDateFormat(dateFormat);
        underTest.setDigitsList(digits);
        underTest.setCurrency(currency);
        List<Object> result = underTest.operate(title, roomName, startDateTime, InValidSeats, false);

        // Then
        assertEquals(List.of("show", 10000, "HUF"), result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(roomRepository).findByRoomName(roomName);
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartsDateTime(movie, room, new SimpleDateFormat(dateFormat).parse(startDateTime));
        Mockito.verify(room).getNumberOfRowsOfChairs();
        Mockito.verify(room).getNumberOfColumnsOfChairs();
        Mockito.verify(bookRepository).findByScreening(screening);
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(book).getSeats();
        Mockito.verify(basePriceRepository).findAll();
        Mockito.verify(listBasePrice).get(0);
        Mockito.verify(basePrice).getBasePriceValue();
        Mockito.verify(room).getComponents();
        Mockito.verify(listComponentPrice, Mockito.times(3)).size();
        Mockito.verify(listComponentPrice, Mockito.times(3)).get(0);
        Mockito.verify(priceComponent, Mockito.times(3)).getPrice();
        Mockito.verify(movie).getComponents();
        Mockito.verify(screening).getComponents();
    }
}
