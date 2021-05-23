package com.epam.training.ticketservice.logic.handler.price.totalprice;

import com.epam.training.ticketservice.database.entity.Seat;
import com.epam.training.ticketservice.logic.command.book.BookCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShowPriceCommandHandlerTest {

    String TITLE;
    String ROOMNAME;
    String STARTSDATETIME;
    String SEATS;

    @Test
    public void testAttachShowPriceForShouldReturnMovieInvalidWhenMovie() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("movie"));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid movie title " + TITLE, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnRoomInvalidWhenRoom() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("room"));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid room name " + ROOMNAME, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnDateFormatInvalidWhenFormat() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("format"));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid date format", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnDateInvalidWhenDate() {
        // Given
        String date = "2012-22-12 12:12";
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("date", date));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid date " + STARTSDATETIME + "this would be " + date, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnScreeningInvalidWhenScreening() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("screening"));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Screening doesn't exist " + TITLE + " " + ROOMNAME + " " + STARTSDATETIME, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnMissingRowNumberWhenRow() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("row"));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Bad input in row numbers, missing row in " + SEATS, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnMissingColumnNumberWhenColumn() {
        // Given
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("column"));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Bad input in column numbers, missing column in " + SEATS, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnTooMuchCommaWhenComma() {
        // Given
        int position = 3;
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of(",", position));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Bad input too much commas in " + SEATS + " at position " + position, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnInvalidCharacterWhenCharacter() {
        // Given
        String inValidCharacter = "A";
        int position = 3;
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of(inValidCharacter, position));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Invalid character in " + SEATS + " " + inValidCharacter + " at position " + position, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnNotExistSeatsWhenExist() {
        // Given
        Seat seat = new Seat(1,3);
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("exist", seat));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Seat " + seat.getRowNumber() + ", " + seat.getColumnNumber() + "does not exist", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnAlreadyTakenSeatsWhenTaken() {
        // Given
        Seat seat = new Seat(1,7);
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("taken", seat));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("Seat " + seat.getRowNumber() + ", " + seat.getColumnNumber() + "is already taken", result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }

    @Test
    public void testAttachShowPriceForShouldReturnPriceForSeatsOnScreeningInPriceAtCurrencyWhenShow() {
        // Given
        int price = 10000;
        String currency = "HUF";
        BookCommand bookCommand = Mockito.mock(BookCommand.class);

        BDDMockito.given(bookCommand.operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false)).willReturn(List.of("show", price, currency));

        ShowPriceCommandHandler underTest = new ShowPriceCommandHandler(bookCommand);

        // When
        String result = underTest.showPriceComponent(TITLE, ROOMNAME, STARTSDATETIME, SEATS);

        // Then
        assertEquals("The price for this booking would be " + price + " " + currency, result);
        Mockito.verify(bookCommand).operate(TITLE, ROOMNAME, STARTSDATETIME, SEATS, false);
    }
}