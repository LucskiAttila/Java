package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignUpCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SignUpCommandHandlerTest {

    String USERNAME = "username";
    String PASSWORD = "password";

    @Test
    public void testSignUpUserShouldReturnSuccessFullySignUpWhenOk() {
        // Given
        SignUpCommand signUpCommand = Mockito.mock(SignUpCommand.class);

        BDDMockito.given(signUpCommand.operate(USERNAME, PASSWORD)).willReturn("ok");

        SignUpCommandHandler underTest = new SignUpCommandHandler(signUpCommand);

        // When
        String result = underTest.signUpUser(USERNAME, PASSWORD);

        // Then
        assertEquals("You successfully signed up with " + USERNAME, result);
        Mockito.verify(signUpCommand).operate(USERNAME, PASSWORD);
    }

    @Test
    public void testSignUpUserShouldReturnAlreadySignUpWhenExist() {
        // Given
        SignUpCommand signUpCommand = Mockito.mock(SignUpCommand.class);

        BDDMockito.given(signUpCommand.operate(USERNAME, PASSWORD)).willReturn("exist");

        SignUpCommandHandler underTest = new SignUpCommandHandler(signUpCommand);

        // When
        String result = underTest.signUpUser(USERNAME, PASSWORD);

        // Then
        assertEquals("You already signed up", result);
        Mockito.verify(signUpCommand).operate(USERNAME, PASSWORD);
    }
}
