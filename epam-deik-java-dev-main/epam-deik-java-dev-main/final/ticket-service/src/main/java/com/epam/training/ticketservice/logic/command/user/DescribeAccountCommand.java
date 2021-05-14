package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.logic.command.AdminAbstract;
import com.epam.training.ticketservice.logic.command.Command;
import com.epam.training.ticketservice.logic.command.book.Seat;
import org.springframework.beans.factory.annotation.Autowired;


import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.logic.command.AdminText;
import org.springframework.beans.factory.annotation.Autowired;
import com.epam.training.ticketservice.database.repository.UserRepository;

import java.util.List;

public class DescribeAccountCommand implements Command {

    @Autowired
    UserRepository userRepository;

    @Value("${CURRENCY}")
    String currency;

    @Override
    public String execute() {
        User user = userRepository.findSingedIn();
        if (user == null) {
            return "You are not signed in";
        } else {
            String username = user.getUserName();
            if (user.getIsAdmin()) {
                return "Signed in with privileged account " + username;
            } else {
                String result = "Signed in with accout " + username + "\n";
                List<Book> books = user.getBook();
                if (books.isEmpty()) {
                   result += "You have not booked any tickets yet";
                }
                else {
                    for(Book book : books) {
                        result += "Seats " + formatSeats(book.getSeats()) + " on " + book.getScreening().getMovie().getTitle() + " in room " + book.getScreening().getRoom().getRoomName() + " starting at " + book.getScreening().getStartsDateAndTime() + " for " + book.getPrice() + " " + currency + "\n";
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
