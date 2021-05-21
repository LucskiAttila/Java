package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SignUpCommandTest {

    @Test
    public void testOperateShouldReturnExistsWhenUserAlreadyExist() {
        // Given
        String username = "username";
        String password = "password";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByUserName(username)).willReturn(user);

        SignUpCommand underTest = new SignUpCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByUserName(username);
    }

    @Test
    public void testOperateShouldReturnOkWhenUserNotExist() {
        // Given
        String username = "username";
        String password = "password";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByUserName(username)).willReturn(null);

        SignUpCommand underTest = new SignUpCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByUserName(username);
        Mockito.verify(userRepository).save(new User (username, password, false, false, Collections.emptyList()));
    }
}
