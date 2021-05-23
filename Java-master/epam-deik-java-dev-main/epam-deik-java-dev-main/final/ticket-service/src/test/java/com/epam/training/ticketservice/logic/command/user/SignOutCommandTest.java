package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SignOutCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNoSignedIn() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        SignOutCommand underTest = new SignOutCommand(userRepository);

        // When
        String result = underTest.operate();

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnOkWhenUserIsAdmin() {
        // Given
        String username = "admin";
        String password = "admin";
        boolean isAdmin = true;
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getBook()).willReturn(Collections.emptyList());
        BDDMockito.given(user.getIsAdmin()).willReturn(isAdmin);
        BDDMockito.given(user.getUserName()).willReturn(username);
        BDDMockito.given(user.getPassword()).willReturn(password);

        SignOutCommand underTest = new SignOutCommand(userRepository);

        // When
        String result = underTest.operate();

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(userRepository).delete(user);
        Mockito.verify(userRepository).save(new User (username, password, isAdmin, false, Collections.emptyList()));
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(user).getBook();
        Mockito.verify(user).getUserName();
        Mockito.verify(user).getPassword();
    }

    @Test
    public void testOperateShouldReturnOkWhenUserIsNotAdmin() {
        // Given
        String username = "username";
        String password = "password";
        boolean isAdmin = false;
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getBook()).willReturn(Collections.emptyList());
        BDDMockito.given(user.getIsAdmin()).willReturn(isAdmin);
        BDDMockito.given(user.getUserName()).willReturn(username);
        BDDMockito.given(user.getPassword()).willReturn(password);

        SignOutCommand underTest = new SignOutCommand(userRepository);

        // When
        String result = underTest.operate();

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(userRepository).delete(user);
        Mockito.verify(userRepository).save(new User (username, password, isAdmin, false, Collections.emptyList()));
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(user).getBook();
        Mockito.verify(user).getUserName();
        Mockito.verify(user).getPassword();
    }
}
