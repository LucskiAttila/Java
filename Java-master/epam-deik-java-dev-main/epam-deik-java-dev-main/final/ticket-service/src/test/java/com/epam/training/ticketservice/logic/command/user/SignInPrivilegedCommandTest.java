package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


class SignInPrivilegedCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        // Given
        String username = "username";
        String password = "password";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);

        SignInPrivilegedCommand underTest = new SignInPrivilegedCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnCredentialsWhenUsernameAndPasswordIncorrect() {
        // Given
        String username = "username";
        String password = "password";
        String validUsername = "admin";
        String validPassword = "admin";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByIsAdmin(true)).willReturn(user);
        BDDMockito.given(user.getUserName()).willReturn(validUsername);
        BDDMockito.given(user.getPassword()).willReturn(validPassword);

        SignInPrivilegedCommand underTest = new SignInPrivilegedCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("credentials", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getUserName();
    }

    @Test void testOperateShouldReturnOkWhenUsernameAndPasswordCorrect() {
        // Given
        String username = "admin";
        String password = "admin";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByIsAdmin(true)).willReturn(user);
        BDDMockito.given(user.getUserName()).willReturn(username);
        BDDMockito.given(user.getPassword()).willReturn(password);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(user.getIsSigned()).willReturn(false);
        BDDMockito.given(user.getBook()).willReturn(Collections.emptyList());

        SignInPrivilegedCommand underTest = new SignInPrivilegedCommand(userRepository);

        // When
        String result = underTest.operate(username, password);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getUserName();
        Mockito.verify(user).getPassword();
        Mockito.verify(user).getBook();
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(user).getIsSigned();
        Mockito.verify(userRepository).delete(user);
        Mockito.verify(userRepository).save(new User (username, password, true, true, Collections.emptyList()));
    }
}
