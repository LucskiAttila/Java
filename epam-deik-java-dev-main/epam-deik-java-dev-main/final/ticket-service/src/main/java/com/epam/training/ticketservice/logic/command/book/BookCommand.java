package com.epam.training.ticketservice.logic.command.book;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.*;
import com.epam.training.ticketservice.logic.command.AdminText;
import com.epam.training.ticketservice.logic.command.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookCommand implements Command {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BasePriceRepository basePriceRepository;

    @Autowired
    @Value("${CURRENCY}")
    String currency;

    @Autowired
    @Value("${DEFAULT_BASE_PRICE}")
    int default_base_price;

    @Autowired
    @Value("${DIGITS}")
    List<Character> digits_list;

    @Autowired
    @Value("${ADMINISTRATOR_USERNAME}")
    String admin_username;

    @Autowired
    @Value("${ADMINISTRATOR_PASSWORD}")
    String admin_password;

    @Autowired
    @Value("${DATE_FORMAT}")
    String dateFormat;

    private final String title;
    private final String roomName;
    private final String startsDateAndTime;
    private final String seats;

    private final boolean shouldSave;

    public BookCommand(String title, String roomName, String startsDateAndTime, String seats, boolean shouldSave) {
        this.title = title;
        this.roomName = roomName;
        this.startsDateAndTime = startsDateAndTime;
        this.seats = seats;
        this.shouldSave = shouldSave;
    }

    private Movie movie;
    private Room room;
    private Screening screening;

    @Override
    public String execute() {
        User user = userRepository.findSingedIn();
        if(!shouldSave && user == null) {
            user = userRepository.save(new User(admin_username, admin_password, true, false, Collections.<Book>emptyList()));
        }
        if (user == null && shouldSave) {
            return "You are not signed in";
        } else {
            String username = user.getUserName();
            if (user.getIsAdmin() && shouldSave) {
                return "Signed in with privileged account " + username;
            } else {
                movie = movieRepository.findByTitle(title);
                if(movie == null) {
                    return "Invalid movie title";
                }
                room = roomRepository.findByroomName(roomName);
                if(room == null) {
                    return "Invalid room name";
                }
                Date startsDateAndTime_date;
                try {
                    startsDateAndTime_date = new SimpleDateFormat(dateFormat).parse(startsDateAndTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return "Invalid date format";
                }
                screening = screeningRepository.findByPrimaryKey(movie, room, startsDateAndTime_date);
                if (screening == null) {
                    return "Screen doesn't exist";
                } else {
                    int rows = room.getNumberOfRowsOfChairs();
                    int cols = room.getNumberOfColumnsOfChairs();
                    List<Book> books_valid = bookRepository.findByScreen(screening);
                    List<Seat> validSeats = new ArrayList<Seat>();
                    for(Book book : books_valid) {
                        validSeats.addAll(book.getSeats());
                    }
                    Result result = convertSeats(seats, validSeats, rows, cols);
                    String state = result.getState();
                    switch(state) {
                        case "ok": {
                            int price = getPrice(result.getSeats().size());
                            if(shouldSave) {
                                Book book = new Book(screening, result.getSeats(), price);
                                bookRepository.save(book);
                                //String userName = user.getUserName();
                                String password = user.getPassword();
                                List<Book> books = user.getBook();
                                books.add(book);
                                userRepository.delete(user);
                                userRepository.save(new User(username, password, false, true, books));
                                return "Seats booked: " + formatSeats(result.getSeats()) + "; the price for this booking is " + price + " " + currency;
                            } else {
                                return "The price for this booking would be" + price + " " + currency;
                            }
                        }
                        case "exist": {
                            Seat bad_seat = result.getSeats().get(0);
                            return  "Seat " + bad_seat.getRow_number() + ", " + bad_seat.getColumn_number() + "does not exist";
                        }
                        case "taken": {
                            Seat bad_seat = result.getSeats().get(0);
                            return "Seat " + bad_seat.getRow_number() + ", " + bad_seat.getColumn_number() + "is already taken";
                        }
                        default: {
                           return "You give " + state;
                        }
                    }
                    //System.gc();
                }
            }
        }
    }

    public int getPrice(int count) {
        int base_price = getBasePrice();
        int components_price = getComponentsPrice();
        return count * (base_price + components_price);
    }

    public int getComponentsPrice() {
        int room_component_price = getComponentPrice(room.getComponents());
        int movie_component_price = getComponentPrice(movie.getComponents());
        int screening_component_price = getComponentPrice(screening.getComponents());
        return room_component_price + movie_component_price + screening_component_price;
    }

    public int getComponentPrice(List<PriceComponent> priceComponents) {
        int result = 0;
        for (PriceComponent priceComponent : priceComponents) {
            result += priceComponent.getPrice();
        }
        return result;
    }

    public int getBasePrice() {
        List<BasePrice> price = basePriceRepository.findAll();
        if(price.isEmpty()) {
            basePriceRepository.save(default_base_price);
            return default_base_price;
        } else {
            return price.get(0).getBase_price();
        }
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

    public Result convertSeats(String seats, List<Seat> validSeats, int rows, int cols) {
        seats += " ";
        List<Character> digits = digits_list;
        List<Seat> seatList = new ArrayList<Seat>();
        String row_str = "";
        String col_str = "";
        int number_of_commas = 0;
        boolean wasComma = false;
        for (int i = 0; i < seats.length(); i++){
            if (seats.charAt(i) == ',') {
                wasComma = true;
                number_of_commas += 1;
            } else if (seats.charAt(i) == ' ') {
                if ("".equals(row_str)) {
                    seatList.clear();
                    return new Result(seatList, "bad input: " + "no row");
                } else if ("".equals(col_str)) {
                    seatList.clear();
                    return new Result(seatList, "bad input: " + "no column");
                } else {
                    int row = Integer.parseInt(row_str);
                    int col = Integer.parseInt(col_str);
                    number_of_commas = 0;
                    row_str = "";
                    col_str = "";
                    wasComma = false;
                    Seat seat = new Seat(row, col);
                    if ((row > rows && col > cols) || (row < 0 && col < 0)) {
                        seatList.clear();
                        seatList.add(seat);
                        return new Result(seatList, "exist");
                    } else if (validSeats.contains(seat)){
                        seatList.clear();
                        seatList.add(seat);
                        return new Result(seatList, "taken");
                    } else{
                        seatList.add(seat);
                    }
                }
            } else if (digits.contains(seats.charAt(i))) {
                if (wasComma) {
                    col_str += seats.charAt(i);
                } else {
                    row_str += seats.charAt(i);
                }
            } else if (number_of_commas > 1) {
                seatList.clear();
                return new Result(seatList, "bad input: " + "too much ,");
            } else {
                seatList.clear();
                return new Result(seatList, "bad input: " + seats.charAt(i));
            }
        }
        return new Result(seatList, "ok");
    }
}
