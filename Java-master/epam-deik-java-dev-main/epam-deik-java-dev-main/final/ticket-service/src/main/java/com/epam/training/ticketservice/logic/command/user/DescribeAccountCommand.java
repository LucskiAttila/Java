package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.entity.Seat;


import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescribeAccountCommand {

    private final UserRepository userRepository;

    @Value("${CURRENCY}")
    String currency;

    private User user;

    public DescribeAccountCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<Object> operate() {
        List<Object> result = new ArrayList<>();
        if (iSUserSignedIn()) {
            if (user.getIsAdmin()) {
                result.add("admin");
                result.add(user.getUserName());
                return result;
            } else {
                result.add("user");
                result.add(user.getUserName());
                result.add(user.getBook());
                result.add(currency);
                return result;
            }
        } else {
            result.add("sign");
            return result;
        }
    }

    private boolean iSUserSignedIn() {
        user = userRepository.findByIsSigned(true);
        return user != null;
    }

    private String formBook(List<Book> books) {
        String result = "";
        for (Book book : books) {
            String element = "";
            Screening screening = book.getScreening();
            element += screening.getMovie().getTitle();
            element += ",";
            element += screening.getRoom().getRoomName();
            element += ",";
            element += screening.getStartsDateTime();
            element += ",";
            element += book.getPrice();
            element += ",";
            element += formSeat(book.getSeats());
            result += element;
            if (book != books.get(books.size()-1)) {
                result += ";";
            }
        }
        return result;
    }

    private String formSeat(List<Seat> seats) {
        String result = "";
        for (Seat seat : seats) {
            String element = "";
            element += seat.getRow_number();
            element += "-";
            element += seat.getColumn_number();
            result += element;
            if (seat!= seats.get(seats.size()-1)) {
                result += ":";
            }
        }
        return result;
    }









    public String nm() {
        User user = userRepository.findByIsSigned(true);
        if (user == null) {
            return "You are not signed in";
        } else {
            String username = user.getUserName();
            if (user.getIsAdmin()) {
                return "Signed in with privileged account " + username;
            } else {
                String result = "Signed in with account " + username + "\n";
                List<Book> books = user.getBook();
                if (books.isEmpty()) {
                    result += "You have not booked any tickets yet";
                }
                else {
                    for(Book book : books) {
                        result += "Seats " + formatSeats(book.getSeats()) + " on " + book.getScreening().getMovie().getTitle() + " in room " + book.getScreening().getRoom().getRoomName() + " starting at " + book.getScreening().getStartsDateTime() + " for " + book.getPrice() + " " + currency + "\n";
                    }
                }
                return result;
            }
        }
    }

    public String formatSeats(List<Seat> seats) {
        String result = "";
        for(int i = 0; i < seats.size(); i++) {
            result += "(" + seats.get(i).getRow_number() + "," + seats.get(i).getColumn_number() + ")";
            if (i != seats.size()-1) {
                result += ", ";
            }
        }
        return result;
    }
}
