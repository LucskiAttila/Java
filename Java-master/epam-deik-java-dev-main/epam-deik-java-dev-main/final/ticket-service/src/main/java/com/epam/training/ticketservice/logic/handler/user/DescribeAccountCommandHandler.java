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

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

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
            return "Signed in with privileged account '" + result.get(1) + "'";
        } else {
            return "Signed in with account '" + result.get(1) + "'\n" + form(result.get(2), result.get(3));
        }
    }

    private String form(Object books, Object currency) {
        if (((List<Book>) books).isEmpty()) {
            return "You have not booked any tickets yet";
        }
        StringBuilder result = new StringBuilder();
        result.append("Your previous bookings are\n");
        for (int i = 0; i < ((List<Book>) books).size(); i++) {
            if (i == (((List<Book>)books).size() - 1)) {
                result.append("Seats " + formatSeats(((List<Book>)books).get(i).getSeats()) + " on " + ((List<Book>)books).get(i).getScreening().getMovie().getTitle() + " in room " + ((List<Book>)books).get(i).getScreening().getRoom().getRoomName() + " starting at " + new SimpleDateFormat(dateFormat).format(((List<Book>)books).get(i).getScreening().getStartsDateTime()) + " for " + ((List<Book>)books).get(i).getPrice() + " " + currency);
            } else {
                result.append("Seats " + formatSeats(((List<Book>)books).get(i).getSeats()) + " on " + ((List<Book>)books).get(i).getScreening().getMovie().getTitle() + " in room " + ((List<Book>)books).get(i).getScreening().getRoom().getRoomName() + " starting at " + new SimpleDateFormat(dateFormat).format(((List<Book>)books).get(i).getScreening().getStartsDateTime()) + " for " + ((List<Book>)books).get(i).getPrice() + " " + currency + "\n");
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
