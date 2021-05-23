package com.epam.training.ticketservice.logic.handler.book;

import com.epam.training.ticketservice.database.entity.Seat;
import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookCommandHandlerTest {

    String TITLE;
    String ROOMNAME;
    String STARTSDATETIME;
    String SEATS;

    @Test
    public void testAttachBookShouldReturnUserNotSignedInWhenSign() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("sign"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("You are not signed in", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnUserSignedInAsAdminWhenAdmin() {
        // Given
        String userName = "admin";
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("admin", userName));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Signed in with privileged account " + userName, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnMovieInvalidWhenMovie() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("movie"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid movie title " + TITLE, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnRoomInvalidWhenRoom() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("room"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid room name " + ROOMNAME, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnDateFormatInvalidWhenFormat() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("format"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid date format", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnDateInvalidWhenDate() {
        // Given
        String date = "2012-22-12 12:12";
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("date", date));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid date " + STARTSDATETIME + "this would be " + date, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnScreeningInvalidWhenScreening() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("screening"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Screening doesn't exist " + TITLE + " " + ROOMNAME + " " + STARTSDATETIME, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnMissingRowNumberWhenRow() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("row"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Bad input in row numbers, missing row in " + SEATS, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnMissingColumnNumberWhenColumn() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("column"));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Bad input in column numbers, missing column in " + SEATS, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnTooMuchCommaWhenComma() {
        // Given
        int position = 3;
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of(",", position));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Bad input too much commas in " + SEATS + " at position " + position, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnInvalidCharacterWhenCharacter() {
        // Given
        String inValidCharacter = "A";
        int position = 3;
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of(inValidCharacter, position));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid character in " + SEATS + " " + inValidCharacter + " at position " + position, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnNotExistSeatsWhenExist() {
        // Given
        Seat seat = new Seat(1,3);
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("exist", seat));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Seat " + seat.getRowNumber() + ", " + seat.getColumnNumber() + "does not exist", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnAlreadyTakenSeatsWhenTaken() {
        // Given
        Seat seat = new Seat(1,7);
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("taken", seat));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Seat " + seat.getRowNumber() + ", " + seat.getColumnNumber() + "is already taken", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }

    @Test
    public void testAttachBookShouldReturnBookSeatsForScreeningInPriceAtCurrencyWhenBook() {
        // Given
        int price = 10000;
        String currency = "HUF";
        int row1 = 1;
        int row2 = 20;
        int col1 = 2;
        int col2 = 1;
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true)).willReturn(List.of("book", List.of(new Seat(row1,col1), new Seat(row2, col2)), price, currency));

        BookCommandHandler underTest = new BookCommandHandler(bookCommand);

        // When
        String result = underTest.book(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Seats booked: " + "(" + row1 + "," +col1 + "), (" + row2 + "," + col2 + ")" + "; the price for this booking is " + price + " " + currency, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, true);
    }
}
