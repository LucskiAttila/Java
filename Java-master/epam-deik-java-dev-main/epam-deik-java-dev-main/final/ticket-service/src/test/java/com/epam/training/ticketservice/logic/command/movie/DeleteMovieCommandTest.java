package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DeleteMovieCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        // Given
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        DeleteMovieCommand underTest = new DeleteMovieCommand(movieRepository, userRepository);

        // When
        String result = underTest.operate(title);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        DeleteMovieCommand underTest = new DeleteMovieCommand(movieRepository, userRepository);

        // When
        String result = underTest.operate(title);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnExistWhenMovieAlreadyExist() {
        // Given
        String title = "Title";
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(null);

        DeleteMovieCommand underTest = new DeleteMovieCommand(movieRepository, userRepository);

        // When
        String result = underTest.operate(title);

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
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        Movie movie = Mockito.mock(Movie.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(title)).willReturn(movie);

        DeleteMovieCommand underTest = new DeleteMovieCommand(movieRepository, userRepository);

        // When
        String result = underTest.operate(title);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(movieRepository).findByTitle(title);
        Mockito.verify(movieRepository).delete(movie);
    }
}
