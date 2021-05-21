package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SignInCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        String username = "username";
        String password = "password";
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);

        SignInCommand underTest = new SignInCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnExistWhenUsernameNotSignedUp() {
        String username = "username";
        String password = "password";
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByUserName(username)).willReturn(null);

        SignInCommand underTest = new SignInCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(userRepository).findByUserName(username);
    }

    @Test
    public void testOperateShouldReturnInvalidWhenInvalidPassword() {
        String username = "username";
        String password = "password";
        String badPassword = "n";
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByUserName(username)).willReturn(user);
        BDDMockito.given(user.getPassword()).willReturn(badPassword);

        SignInCommand underTest = new SignInCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("invalid", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(userRepository).findByUserName(username);
        Mockito.verify(user).getPassword();
    }

    @Test
    public void testOperateShouldReturnOkWhenValidUsernameAndPassword() {
        String username = "username";
        String password = "password";
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByUserName(username)).willReturn(user);
        BDDMockito.given(user.getPassword()).willReturn(password);
        BDDMockito.given(user.getBook()).willReturn(Collections.emptyList());

        SignInCommand underTest = new SignInCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(userRepository).findByUserName(username);
        Mockito.verify(user).getPassword();
        Mockito.verify(user).getBook();
        Mockito.verify(userRepository).delete(user);
        Mockito.verify(userRepository).save(new User (username, password, false, true, Collections.emptyList()));
    }
}
