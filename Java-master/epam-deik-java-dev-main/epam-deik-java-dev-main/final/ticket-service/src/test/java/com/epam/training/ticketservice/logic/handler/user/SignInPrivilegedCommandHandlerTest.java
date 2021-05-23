package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignInPrivilegedCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SignInPrivilegedCommandHandlerTest {

    String USERNAME = "username";
    String PASSWORD = "password";

    @Test
    public void testSignInUserShouldReturnSuccessFullySignInWhenOk() {
        // Given
        SignInPrivilegedCommand signInPrivilegedCommand = Mockito.mock(SignInPrivilegedCommand.class);

        BDDMockito.given(signInPrivilegedCommand.operate(USERNAME, PASSWORD)).willReturn("ok");

        SignInPrivilegedCommandHandler underTest = new SignInPrivilegedCommandHandler(signInPrivilegedCommand);

        // When
        String result = underTest.signInUser(USERNAME, PASSWORD);

        // Then
        assertEquals("You successfully signed in as administrator", result);
        Mockito.verify(signInPrivilegedCommand).operate(USERNAME, PASSWORD);
    }

    @Test
    public void testSignInUserShouldReturnSuccessIncorrectCredentialsWhenCredentials() {
        // Given
        SignInPrivilegedCommand signInPrivilegedCommand = Mockito.mock(SignInPrivilegedCommand.class);

        BDDMockito.given(signInPrivilegedCommand.operate(USERNAME, PASSWORD)).willReturn("credentials");

        SignInPrivilegedCommandHandler underTest = new SignInPrivilegedCommandHandler(signInPrivilegedCommand);

        // When
        String result = underTest.signInUser(USERNAME, PASSWORD);

        // Then
        assertEquals("Login failed due to incorrect credentials", result);
        Mockito.verify(signInPrivilegedCommand).operate(USERNAME, PASSWORD);
    }

    @Test
    public void testSignInUserShouldReturnInvalidDateFormatWhenFormat() {
        // Given
        SignInPrivilegedCommand signInPrivilegedCommand = Mockito.mock(SignInPrivilegedCommand.class);

        BDDMockito.given(signInPrivilegedCommand.operate(USERNAME, PASSWORD)).willReturn("sign");

        SignInPrivilegedCommandHandler underTest = new SignInPrivilegedCommandHandler(signInPrivilegedCommand);

        // When
        String result = underTest.signInUser(USERNAME, PASSWORD);

        // Then
        assertEquals("You already signed in", result);
        Mockito.verify(signInPrivilegedCommand).operate(USERNAME, PASSWORD);
    }
}
