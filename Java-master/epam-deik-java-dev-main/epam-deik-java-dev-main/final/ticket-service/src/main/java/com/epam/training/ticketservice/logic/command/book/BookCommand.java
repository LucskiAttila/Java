package com.epam.training.ticketservice.logic.command.book;

import com.epam.training.ticketservice.database.entity.*;
import com.epam.training.ticketservice.database.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BookCommand {

    private final BookRepository bookRepository;
    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final BasePriceRepository basePriceRepository;

    @Value("${CURRENCY}")
    String currency;

    @Value("${DEFAULT_BASE_PRICE}")
    int default_base_price;

    @Value("${DIGITS}")
    List<Character> digits_list;

    @Value("${ADMINISTRATOR_USERNAME}")
    String admin_username;

    @Value("${ADMINISTRATOR_PASSWORD}")
    String admin_password;

    @Value("${DATE_FORMAT}")
    String dateFormat;

    public BookCommand(BookRepository bookRepository, ScreeningRepository screeningRepository, RoomRepository roomRepository, MovieRepository movieRepository, UserRepository userRepository, BasePriceRepository basePriceRepository) {
        this.bookRepository = bookRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.basePriceRepository = basePriceRepository;
    }

    private Movie movie;
    private Room room;
    private Screening screening;

    @Transactional
    public String operate(String title, String roomName, String startsDateAndTime, String seats, boolean shouldSave) {
        User user = userRepository.findByIsSigned(true);
        if(!shouldSave && user == null) {
            user = userRepository.findByIsAdmin(true);
            //user = userRepository.save(new User(admin_username, admin_password, true, false, Collections.<Book>emptyList()));
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
                room = roomRepository.findByRoomName(roomName);
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
                if (!new SimpleDateFormat(dateFormat).format(startsDateAndTime_date).equals(startsDateAndTime)) {
                    return "Invalid date";
                }
                screening = screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room, startsDateAndTime_date);
                if (screening == null) {
                    return "Screen doesn't exist";
                } else {
                    int rows = room.getNumberOfRowsOfChairs();
                    int cols = room.getNumberOfColumnsOfChairs();
                    List<Book> books_valid = bookRepository.findByScreening(screening);
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
                                return "The price for this booking would be " + price + " " + currency;
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
        List<BasePrice> price = (List<BasePrice>) basePriceRepository.findAll();
        if(price.isEmpty()) {
            basePriceRepository.save(new BasePrice(default_base_price));
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
