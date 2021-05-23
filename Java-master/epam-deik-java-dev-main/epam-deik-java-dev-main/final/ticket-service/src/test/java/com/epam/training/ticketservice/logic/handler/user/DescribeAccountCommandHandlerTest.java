package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.logic.command.user.DescribeAccountCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DescribeAccountCommandHandlerTest {

    @Test
    public void testDescribeUserShouldReturnNotSignInWhenSign() {
        // Given
        DescribeAccountCommand describeAccountCommand = Mockito.mock(DescribeAccountCommand.class);

        List<Object> resultList = new ArrayList<>();
        resultList.add("sign");
        BDDMockito.given(describeAccountCommand.operate()).willReturn(resultList);

        DescribeAccountCommandHandler underTest = new DescribeAccountCommandHandler(describeAccountCommand);

        // When
        String result = underTest.describeUser();

        // Then
        assertEquals("You are not signed in", result);
        Mockito.verify(describeAccountCommand).operate();
    }

    @Test
    public void testDescribeUserShouldReturnPrivilegedAccountWithUsernameWhenAdmin() {
        // Given
        String username = "admin";
        DescribeAccountCommand describeAccountCommand = Mockito.mock(DescribeAccountCommand.class);

        List<Object> resultList = new ArrayList<>();
        resultList.add("admin");
        resultList.add(username);
        BDDMockito.given(describeAccountCommand.operate()).willReturn(resultList);

        DescribeAccountCommandHandler underTest = new DescribeAccountCommandHandler(describeAccountCommand);

        // When
        String result = underTest.describeUser();

        // Then
        assertEquals("Signed in with privileged account '" + username + "'", result);
        Mockito.verify(describeAccountCommand).operate();
    }

    @Test
    public void testDescribeUserShouldReturnSinInWithAccountUsernameWithNoBookingsWhenNotAdmin() {
        // Given
        String username = "admin";
        String CURRENCY = "HUF";
        DescribeAccountCommand describeAccountCommand = Mockito.mock(DescribeAccountCommand.class);

        List<Object> resultList = new ArrayList<>();
        resultList.add("user");
        resultList.add(username);
        resultList.add(Collections.emptyList());
        resultList.add(CURRENCY);
        BDDMockito.given(describeAccountCommand.operate()).willReturn(resultList);

        DescribeAccountCommandHandler underTest = new DescribeAccountCommandHandler(describeAccountCommand);

        // When
        String result = underTest.describeUser();

        // Then
        assertEquals("Signed in with account '" + username + "'\n" + "You have not booked any tickets yet", result);
        Mockito.verify(describeAccountCommand).operate();
    }

    @Test
    public void testDescribeUserShouldReturnSinInWithAccountUsernameWithBookingsWhenNotAdmin() {
        // Given
        String errorMessage = "user";
        String username = "admin";
        String CURRENCY = "HUF";
        String title = "Title";
        String roomName = "RoomName";
        String dateFormat = "yyyy-MM-dd HH:mm";
        String dateString = "2012-12-12 12:12";
        int price = 1500;
        Date date = null;
        try {
            date = new SimpleDateFormat(dateFormat).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int rowNumber = 1;
        int colNumber = 2;
        int numberOfSeats = 2;
        int numberOfBooks = 2;
        int numberOfResults = 4;

        DescribeAccountCommand describeAccountCommand = Mockito.mock(DescribeAccountCommand.class);
        Book book = Mockito.mock(Book.class);
        Seat seat = Mockito.mock(Seat.class);
        List results = Mockito.mock(List.class);
        List books = Mockito.mock(List.class);
        List seats = Mockito.mock(List.class);
        Screening screening = Mockito.mock(Screening.class);
        Movie movie = Mockito.mock(Movie.class);
        Room room = Mockito.mock(Room.class);

        BDDMockito.given(book.getSeats()).willReturn(seats);
        BDDMockito.given(book.getScreening()).willReturn(screening);
        BDDMockito.given(book.getScreening().getMovie()).willReturn(movie);
        BDDMockito.given(book.getScreening().getMovie().getTitle()).willReturn(title);
        BDDMockito.given(book.getScreening().getRoom()).willReturn(room);
        BDDMockito.given(book.getScreening().getRoom().getRoomName()).willReturn(roomName);
        BDDMockito.given(book.getScreening().getStartsDateTime()).willReturn(date);
        BDDMockito.given(book.getPrice()).willReturn(price);

        BDDMockito.given(seat.getRowNumber()).willReturn(rowNumber);
        BDDMockito.given(seat.getColumnNumber()).willReturn(colNumber);

        BDDMockito.given(describeAccountCommand.operate()).willReturn(results);

        BDDMockito.given(seats.size()).willReturn(numberOfSeats);
        BDDMockito.given(seats.get(0)).willReturn(seat);
        BDDMockito.given(seats.get(1)).willReturn(seat);

        BDDMockito.given(books.size()).willReturn(numberOfBooks);
        BDDMockito.given(books.isEmpty()).willReturn(false);
        BDDMockito.given(books.get(0)).willReturn(book);
        BDDMockito.given(books.get(1)).willReturn(book);

        BDDMockito.given(results.get(0)).willReturn(errorMessage);
        BDDMockito.given(results.get(1)).willReturn(username);
        BDDMockito.given(results.get(2)).willReturn(books);
        BDDMockito.given(results.get(3)).willReturn(CURRENCY);

        DescribeAccountCommandHandler underTest = new DescribeAccountCommandHandler(describeAccountCommand);
        underTest.setDateFormat(dateFormat);

        // When
        String result = underTest.describeUser();

        // Then
        String expected = "Seats (" + rowNumber + "," + colNumber + "), (" + rowNumber + "," + colNumber +") on " + title + " in room " + roomName + " starting at " + dateString + " for "+ price + " " + CURRENCY;
        assertEquals("Signed in with account '" + username + "'\n" + "Your previous bookings are\n" + expected + "\n" + expected, result);
        Mockito.verify(describeAccountCommand).operate();
    }
}