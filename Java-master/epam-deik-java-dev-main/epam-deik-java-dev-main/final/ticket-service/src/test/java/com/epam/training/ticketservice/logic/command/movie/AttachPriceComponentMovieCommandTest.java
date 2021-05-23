package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttachPriceComponentMovieCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String name = "Name";
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String name = "Name";
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnFirstWhenMovieParameterInValid() {
        // Given
        String name = "Name";
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("first", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnSecondWhenPriceComponentParameterInValid() {
        // Given
        String name = "Name";
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("second", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnAllWhenPriceMovieAndComponentParameterInValid() {
        // Given
        String name = "Name";
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("all", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnMoreWhenAlreadyAttachedAndNotValidDuplication() {
        // Given
        String name = "Name";
        String title = "Title";
        int listSize = 1;
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(movie.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(name);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);
        underTest.setCanAttachMore(false);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("more", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(movie).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
    }

    @Test
    public void testOperateShouldReturnOkWhenAttachedNotExistAndNotValidDuplication() {
        // Given
        String name = "Name";
        String title = "Title";
        String OtherName = "OtherName";
        String genre = "drama";
        int durationTime = 10;
        int listSize = 1;
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(movie.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(OtherName);
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(durationTime);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);
        underTest.setCanAttachMore(false);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(movie).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(movieRepository).delete(movie);
        Mockito.verify(movieRepository).save(new Movie(title, genre, durationTime, list));
    }

    @Test
    public void testOperateShouldReturnOkWhenAttachedNotExistAndValidDuplication() {
        // Given
        String name = "Name";
        String title = "Title";
        String OtherName = "OtherName";
        String genre = "drama";
        int durationTime = 10;
        int listSize = 1;
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(movie.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(OtherName);
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(durationTime);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);
        underTest.setCanAttachMore(true);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(movie).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(movieRepository).delete(movie);
        Mockito.verify(movieRepository).save(new Movie(title, genre, durationTime, list));
    }

    @Test
    public void testOperateShouldReturnOkDuplicateWhenAttachedNotExistAndValidDuplication() {
        // Given
        String name = "Name";
        String title = "Title";
        String genre = "drama";
        int durationTime = 10;
        int listSize = 1;
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);
        List<PriceComponent> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);
        BDDMockito.given(movie.getComponents()).willReturn(list);
        BDDMockito.given(list.size()).willReturn(listSize);
        BDDMockito.given(list.get(0)).willReturn(priceComponent);
        BDDMockito.given(priceComponent.getName()).willReturn(name);
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(durationTime);

        AttachPriceComponentMovieCommand underTest = new AttachPriceComponentMovieCommand(movieRepository, priceComponentRepository, userRepository);
        underTest.setCanAttachMore(true);

        // When
        String result = underTest.operate(name, title);

        // Then
        assertEquals("okDuplicate", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(movie).getComponents();
        Mockito.verify(list).size();
        Mockito.verify(list).get(0);
        Mockito.verify(priceComponent).getName();
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
        Mockito.verify(list).add(priceComponent);
        Mockito.verify(movieRepository).delete(movie);
        Mockito.verify(movieRepository).save(new Movie(title, genre, durationTime, list));
    }
}
