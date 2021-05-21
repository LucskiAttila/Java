package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignOutCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SignOutCommandHandlerTest {

    @Test
    public void testSignOutUserShouldReturnYouAreNotSignedInWhenSign() {
        // Given
        SignOutCommand signOutCommand = Mockito.mock(SignOutCommand.class);

        BDDMockito.given(signOutCommand.operate()).willReturn("sign");

        SignOutCommandHandler underTest = new SignOutCommandHandler(signOutCommand);

        // When
        String result = underTest.signOutUser();

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(signOutCommand).operate();
    }

    @Test
    public void testSignOutUserShouldReturnYouSuccessfullySignedOutWhenOk() {
        // Given
        SignOutCommand signOutCommand = Mockito.mock(SignOutCommand.class);

        BDDMockito.given(signOutCommand.operate()).willReturn("ok");

        SignOutCommandHandler underTest = new SignOutCommandHandler(signOutCommand);

        // When
        String result = underTest.signOutUser();

        // Then
        assertEquals("You successfully signed out", result);
        Mockito.verify(signOutCommand).operate();
    }
}
