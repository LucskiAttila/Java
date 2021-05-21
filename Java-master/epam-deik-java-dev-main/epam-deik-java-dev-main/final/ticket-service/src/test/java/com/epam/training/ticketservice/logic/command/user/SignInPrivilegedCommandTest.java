package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


class SignInPrivilegedCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserAlreadySignedIn() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);

        SignInPrivilegedCommand underTest = new SignInPrivilegedCommand(userRepository);

        // When
        String result = underTest.operate("name", "password");

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnCredentialsWhenUsernameAndPasswordIncorrect() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);
        BDDMockito.given(userRepository.findByIsAdmin(true)).willReturn(user);
        BDDMockito.given(user.getUserName()).willReturn("admin");
        BDDMockito.given(user.getPassword()).willReturn("admin");

        SignInPrivilegedCommand underTest = new SignInPrivilegedCommand(userRepository);

        // When
        String result = underTest.operate("name", "password");

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