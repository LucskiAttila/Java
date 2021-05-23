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

class UpdateMovieCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);

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

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);

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

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, invalidDurationInMinutes);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnExistWhenMovieNotExist() {
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

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);
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
    public void testOperateShouldReturnFirstWhenSecondParamCanBeUpdate() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        int ValidDurationInMinutes = 1;
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(ValidDurationInMinutes);
        BDDMockito.given(movie.getComponents()).willReturn(Collections.emptyList());
        BDDMockito.given(movie.getTitle()).willReturn(title);

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("first", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(movieRepository).delete(movie);
        Mockito.verify(movieRepository).save(new Movie (title, genre, Integer.parseInt(durationInMinutes), Collections.emptyList()));
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
        Mockito.verify(movie).getComponents();
        Mockito.verify(movie).getTitle();
    }

    @Test
    public void testOperateShouldReturnSecondWhenFirstParamCanBeUpdate() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        String ValidGenre = "G";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(movie.getGenre()).willReturn(ValidGenre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(Integer.parseInt(durationInMinutes));
        BDDMockito.given(movie.getComponents()).willReturn(Collections.emptyList());
        BDDMockito.given(movie.getTitle()).willReturn(title);

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("second", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(movieRepository).delete(movie);
        Mockito.verify(movieRepository).save(new Movie (title, genre, Integer.parseInt(durationInMinutes), Collections.emptyList()));
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
        Mockito.verify(movie).getComponents();
        Mockito.verify(movie).getTitle();
    }

    @Test
    public void testOperateShouldReturnNothingWhenAllParamCanBeUpdate() {
        // Given
        String title = "Title";
        String genre = "Genre";
        String durationInMinutes = "10";
        int ValidDurationInMinutes = 1;
        String ValidGenre = "G";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);
        BDDMockito.given(movie.getGenre()).willReturn(ValidGenre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(ValidDurationInMinutes);
        BDDMockito.given(movie.getComponents()).willReturn(Collections.emptyList());
        BDDMockito.given(movie.getTitle()).willReturn(title);

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(movieRepository).delete(movie);
        Mockito.verify(movieRepository).save(new Movie (title, genre, Integer.parseInt(durationInMinutes), Collections.emptyList()));
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
        Mockito.verify(movie).getComponents();
        Mockito.verify(movie).getTitle();
    }

    @Test
    public void testOperateShouldReturnAllWhenNoParamCanBeUpdate() {
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
        BDDMockito.given(movie.getGenre()).willReturn(genre);
        BDDMockito.given(movie.getDurationInMinutes()).willReturn(Integer.parseInt(durationInMinutes));

        UpdateMovieCommand underTest = new UpdateMovieCommand(movieRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(title, genre, durationInMinutes);

        // Then
        assertEquals("all", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(movie).getGenre();
        Mockito.verify(movie).getDurationInMinutes();
    }
}
