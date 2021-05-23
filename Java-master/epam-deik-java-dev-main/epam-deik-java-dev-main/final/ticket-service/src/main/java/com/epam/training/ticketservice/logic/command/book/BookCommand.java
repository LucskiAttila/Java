package com.epam.training.ticketservice.logic.command.book;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.Room;
import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.Seat;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.repository.BookRepository;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import com.epam.training.ticketservice.database.repository.BasePriceRepository;
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

    @Value("${DATE_FORMAT_VALID}")
    String dateFormatValid;

    @Value("${DIGITS}")
    List<Character> digitsList;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDigitsList(List<Character> digitsList) {
        this.digitsList = digitsList;
    }

    public void setDateFormatValid(String dateFormatValid) {
        this.dateFormatValid = dateFormatValid;
    }

    @Value("${DATE_FORMAT}")
    String dateFormat;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public BookCommand(BookRepository bookRepository, ScreeningRepository screeningRepository,
                       RoomRepository roomRepository, MovieRepository movieRepository,
                       UserRepository userRepository, BasePriceRepository basePriceRepository) {
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
    public List<Object> operate(String title, String roomName, String startsDateAndTime,
                                String seats, boolean shouldSave) {
        List<Object> result = new ArrayList<Object>();
        User user = userRepository.findByIsSigned(true);
        if (!shouldSave && user == null) {
            user = userRepository.findByIsAdmin(true);
        }
        if (user == null && shouldSave) {
            result.add("sign");
            return result;
        } else {
            String username = user.getUserName();
            if (user.getIsAdmin() && shouldSave) {
                result.add("admin");
                result.add(username);
                return result;
            } else {
                movie = movieRepository.findByTitle(title);
                if (movie == null) {
                    result.add("movie");
                    return result;
                }
                room = roomRepository.findByRoomName(roomName);
                if (room == null) {
                    result.add("room");
                    return result;
                }
                if (!startsDateAndTime.matches(dateFormatValid)) {
                    result.add("format");
                    return result;
                }
                Date startsDateAndTimeFormatDate = null;
                try {
                    startsDateAndTimeFormatDate = new SimpleDateFormat(dateFormat).parse(startsDateAndTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String actualDate = new SimpleDateFormat(dateFormat).format(startsDateAndTimeFormatDate);
                if (!startsDateAndTime.equals(actualDate)) {
                    result.add("date");
                    result.add(actualDate);
                    return result;
                }
                actualDate = null;
                screening = screeningRepository.findByMovieAndRoomAndStartsDateTime(movie, room,
                        startsDateAndTimeFormatDate);
                if (screening == null) {
                    result.add("screening");
                    return result;
                } else {
                    int rows = room.getNumberOfRowsOfChairs();
                    int cols = room.getNumberOfColumnsOfChairs();
                    List<Book> booksValid = bookRepository.findByScreening(screening);
                    int size = booksValid.size();
                    List<Seat> validSeats = new ArrayList<Seat>();
                    for (int i = 0; i < size; i++) {
                        List<Seat> actual = booksValid.get(i).getSeats();
                        int actualSize = actual.size();
                        for (int j = 0; j < actualSize; j++) {
                            validSeats.add(actual.get(j));
                        }
                    }
                    List<Object> state = convertSeats(seats, validSeats, rows, cols);
                    if (("ok").equals(state.get(0))) {
                        List<Seat> list = (List<Seat>) state.get(1);
                        int price = getPrice((list).size());
                        if (shouldSave) {
                            Book book = new Book(screening, list, price);
                            bookRepository.save(book);
                            String password = user.getPassword();
                            List<Book> books = user.getBook();
                            books.add(book);
                            userRepository.delete(user);
                            userRepository.save(new User(username, password, false, true, books));
                            state.set(0, "book");
                            state.add(price);
                            state.add(currency);
                        } else {
                            state.clear();
                            state.add("show");
                            state.add(price);
                            state.add(currency);
                        }
                    }
                    return state;
                }
            }
        }
    }

    private int getPrice(int count) {
        int basePrice = getBasePrice();
        int componentsPrice = getComponentsPrice();
        return count * (basePrice + componentsPrice);
    }

    private int getComponentsPrice() {
        int roomComponentPrice = getComponentPrice(room.getComponents());
        int movieComponentPrice = getComponentPrice(movie.getComponents());
        int screeningComponentPrice = getComponentPrice(screening.getComponents());
        return roomComponentPrice + movieComponentPrice + screeningComponentPrice;
    }

    private int getComponentPrice(List<PriceComponent> priceComponents) {
        int result = 0;
        int size = priceComponents.size();
        for (int i = 0; i < size; i++) {
            result += priceComponents.get(i).getPrice();
        }
        return result;
    }

    private int getBasePrice() {
        return basePriceRepository.findAll().get(0).getBasePriceValue();
    }

    private List<Object> convertSeats(String seats, List<Seat> validSeats, int rows, int cols) {
        int minSeatRowNumber = 1;
        int minSeatColumnNUmber = 1;
        seats += " ";
        List<Seat> seatList = new ArrayList<Seat>();
        List<Object> result = new ArrayList<Object>();
        String rowStr = "";
        String colStr = "";
        int numberOfCommas = 0;
        boolean wasComma = false;
        int length = seats.length();
        for (int i = 0; i < length; i++) {
            if (seats.charAt(i) == ',') {
                wasComma = true;
                numberOfCommas += 1;
            } else if (numberOfCommas > 1) {
                seatList.clear();
                result.add(",");
                result.add(i - 1);
                return result;
            } else if (seats.charAt(i) == ' ') {
                if ("".equals(rowStr)) {
                    seatList.clear();
                    result.add("row");
                    return result;
                } else if ("".equals(colStr)) {
                    seatList.clear();
                    result.add("column");
                    return result;
                } else {
                    numberOfCommas = 0;
                    wasComma = false;
                    int row = Integer.parseInt(rowStr);
                    int col = Integer.parseInt(colStr);
                    rowStr = "";
                    colStr = "";
                    Seat seat = new Seat(row, col);
                    if ((row > rows || col > cols) || (row < minSeatRowNumber || col < minSeatColumnNUmber)) {
                        seatList.clear();
                        seatList.add(seat);
                        result.add("exist");
                        result.add(seat);
                        return result;
                    } else if (validSeats.contains(seat)) {
                        seatList.clear();
                        seatList.add(seat);
                        result.add("taken");
                        result.add(seat);
                        return result;
                    } else {
                        seatList.add(seat);
                    }
                }
            } else if (digitsList.contains(seats.charAt(i))) {
                if (wasComma) {
                    colStr += seats.charAt(i);
                } else {
                    rowStr += seats.charAt(i);
                }
            } else {
                seatList.clear();
                result.add(String.valueOf(seats.charAt(i)));
                result.add(i);
                return result;
            }
        }
        result.add("ok");
        result.add(seatList);
        return result;
    }
}
