package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignInCommand;
import com.epam.training.ticketservice.logic.command.user.SignInPrivilegedCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SignInCommandHandlerTest {

    String USERNAME = "username";
    String PASSWORD = "password";

    @Test
    public void testSignInNoAdminUserShouldReturnSuccessFullySignInWhenOk() {
        // Given
        SignInCommand signInCommand = Mockito.mock(SignInCommand.class);

        BDDMockito.given(signInCommand.operate(USERNAME, PASSWORD)).willReturn("ok");

        SignInCommandHandler underTest = new SignInCommandHandler(signInCommand);

        // When
        String result = underTest.signInNoAdminUser(USERNAME, PASSWORD);

        // Then
        assertEquals("You successfully signed in with " + USERNAME, result);
        Mockito.verify(signInCommand).operate(USERNAME, PASSWORD);
    }

    @Test
    public void testSignInNoAdminUserShouldReturnUserAlreadySignedInWhenSign() {
        // Given
        SignInCommand signInCommand = Mockito.mock(SignInCommand.class);

        BDDMockito.given(signInCommand.operate(USERNAME, PASSWORD)).willReturn("sign");

        SignInCommandHandler underTest = new SignInCommandHandler(signInCommand);

        // When
        String result = underTest.signInNoAdminUser(USERNAME, PASSWORD);

        // Then
        assertEquals("You already signed in", result);
        Mockito.verify(signInCommand).operate(USERNAME, PASSWORD);
    }

    @Test
    public void testSignInNoAdminUserShouldReturnUserNotExistInWhenExist() {
        // Given
        SignInCommand signInCommand = Mockito.mock(SignInCommand.class);

        BDDMockito.given(signInCommand.operate(USERNAME, PASSWORD)).willReturn("exist");

        SignInCommandHandler underTest = new SignInCommandHandler(signInCommand);

        // When
        String result = underTest.signInNoAdminUser(USERNAME, PASSWORD);

        // Then
        assertEquals("No user with " + USERNAME, result);
        Mockito.verify(signInCommand).operate(USERNAME, PASSWORD);
    }

    @Test
    public void testSignInNoAdminUserShouldReturnInvalidPasswordWhenInvalid() {
        // Given
        SignInCommand signInCommand = Mockito.mock(SignInCommand.class);

        BDDMockito.given(signInCommand.operate(USERNAME, PASSWORD)).willReturn("invalid");

        SignInCommandHandler underTest = new SignInCommandHandler(signInCommand);

        // When
        String result = underTest.signInNoAdminUser(USERNAME, PASSWORD);

        // Then
        assertEquals("Login failed due to incorrect credentials", result);
        Mockito.verify(signInCommand).operate(USERNAME, PASSWORD);
    }
}
