package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.Seat;
import com.epam.training.ticketservice.logic.command.user.DescribeAccountCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@ShellComponent
public class DescribeAccountCommandHandler {

    @Value("${DATE_FORMAT}")
    private String dateFormat;

    private final DescribeAccountCommand describeAccountCommand;

    public DescribeAccountCommandHandler(DescribeAccountCommand describeAccountCommand) {
        this.describeAccountCommand = describeAccountCommand;
    }

    @ShellMethod(value = "Get information of your account", key = "describe account")
    @Transactional
    public String describeUser() {
        List<Object> result = describeAccountCommand.operate();
        if ("sign".equals(result.get(0))) {
            return "You are not signed in";
        } else if ("admin".equals(result.get(0))){
            return "Signed in with privileged account '" + result.get(1) + "'\n" + "You have not booked any tickets yet";
        } else {
            return "Signed in with account '" + result.get(1) + "'\n" + "Your previous bookings are\n" + form(result.get(2), result.get(3));
        }
    }

    private String form(Object books, Object currency) {
        StringBuilder result = new StringBuilder();
        for (Book book : (List<Book>) books) {
            if (book == ((List<Book>)books).get(((List<Book>)books).size() - 1)) {
                result.append("Seats " + formatSeats(book.getSeats()) + " on " + book.getScreening().getMovie().getTitle() + " in room " + book.getScreening().getRoom().getRoomName() + " starting at " + new SimpleDateFormat(dateFormat).format(book.getScreening().getStartsDateTime()) + " for " + book.getPrice() + " " + currency);
            } else {
                result.append("Seats " + formatSeats(book.getSeats()) + " on " + book.getScreening().getMovie().getTitle() + " in room " + book.getScreening().getRoom().getRoomName() + " starting at " + new SimpleDateFormat(dateFormat).format(book.getScreening().getStartsDateTime()) + " for " + book.getPrice() + " " + currency + "\n");
            }
        }
        return result.toString();
    }

    public String formatSeats(List<Seat> seats) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < seats.size(); i++) {
            result.append("(").append(seats.get(i).getRow_number()).append(",").append(seats.get(i).getColumn_number()).append(")");
            if (i != seats.size()-1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
