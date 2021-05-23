package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateMovieCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        CreateMovieCommand underTest = new CreateMovieCommand(movieRepository, userRepository);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        CreateMovieCommand underTest = new CreateMovieCommand(movieRepository, userRepository);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnBadStringWhenDurationInMinutesIsInvalid() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String invalidDurationInMinutes = "A0";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        CreateMovieCommand underTest = new CreateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, invalidDurationInMinutes);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }


    @Test
    public void testOperateShouldReturnExistWhenMovieAlreadyExist() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);

        CreateMovieCommand underTest = new CreateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
    }

    @Test
    public void testOperateShouldReturnOkWhenMovieCanBeCreate() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);

        CreateMovieCommand underTest = new CreateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(movieRepository).save(new Movie (title, genre, 10, Collections.emptyList()));
    }
}
