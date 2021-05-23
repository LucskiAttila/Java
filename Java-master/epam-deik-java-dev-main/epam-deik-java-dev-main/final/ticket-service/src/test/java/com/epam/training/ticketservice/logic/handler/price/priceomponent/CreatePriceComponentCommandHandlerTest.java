package com.epam.training.ticketservice.logic.handler.price.priceomponent;

import com.epam.training.ticketservice.logic.command.price.pricecomponent.CreatePriceComponentCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class CreatePriceComponentCommandHandlerTest {

    String NAME = "Name";
    String PRICE = "Price";

    @Test
    public void testCreatePriceComponentShouldReturnSuccessFullyCreatePriceComponentWhenOk() {
        // Given
        CreatePriceComponentCommand createPriceComponentCommand = Mockito.mock(CreatePriceComponentCommand.class);

        BDDMockito.given(createPriceComponentCommand.operate(NAME, PRICE)).willReturn("ok");

        CreatePriceComponentCommandHandler underTest = new CreatePriceComponentCommandHandler(createPriceComponentCommand);

        // When
        String result = underTest.createPriceComponent(NAME, PRICE);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " price component is successfully created", result);
        Mockito.verify(createPriceComponentCommand).operate(NAME, PRICE);
    }

    @Test
    public void testCreatePriceComponentShouldReturnPriceComponentAlreadyExistWhenExist() {
        // Given
        CreatePriceComponentCommand createPriceComponentCommand = Mockito.mock(CreatePriceComponentCommand.class);

        BDDMockito.given(createPriceComponentCommand.operate(NAME, PRICE)).willReturn("exist");

        CreatePriceComponentCommandHandler underTest = new CreatePriceComponentCommandHandler(createPriceComponentCommand);

        // When
        String result = underTest.createPriceComponent(NAME, PRICE);

        // Then
        assertEquals(StringUtils.capitalize(NAME) + " price component is already exists", result);
        Mockito.verify(createPriceComponentCommand).operate(NAME, PRICE);
    }

    @Test
    public void testCreatePriceComponentShouldReturnUserNotSignedInWhenSign() {
        // Given
        CreatePriceComponentCommand createPriceComponentCommand = Mockito.mock(CreatePriceComponentCommand.class);

        BDDMockito.given(createPriceComponentCommand.operate(NAME, PRICE)).willReturn("sign");

        CreatePriceComponentCommandHandler underTest = new CreatePriceComponentCommandHandler(createPriceComponentCommand);

        // When
        String result = underTest.createPriceComponent(NAME, PRICE);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(createPriceComponentCommand).operate(NAME, PRICE);
    }

    @Test
    public void testCreatePriceComponentShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        CreatePriceComponentCommand createPriceComponentCommand = Mockito.mock(CreatePriceComponentCommand.class);

        BDDMockito.given(createPriceComponentCommand.operate(NAME, PRICE)).willReturn("admin");

        CreatePriceComponentCommandHandler underTest = new CreatePriceComponentCommandHandler(createPriceComponentCommand);

        // When
        String result = underTest.createPriceComponent(NAME, PRICE);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(createPriceComponentCommand).operate(NAME, PRICE);
    }

    @Test
    public void testCreatePriceComponentShouldReturnInvalidPriceWhenBadString() {
        // Given
        String badString = "S";
        // Given
        CreatePriceComponentCommand createPriceComponentCommand = Mockito.mock(CreatePriceComponentCommand.class);

        BDDMockito.given(createPriceComponentCommand.operate(NAME, PRICE)).willReturn(badString);

        CreatePriceComponentCommandHandler underTest = new CreatePriceComponentCommandHandler(createPriceComponentCommand);

        // When
        String result = underTest.createPriceComponent(NAME, PRICE);

        // Then
        assertEquals("You add invalid integer " + badString + " in " + PRICE, result);
        Mockito.verify(createPriceComponentCommand).operate(NAME, PRICE);
    }
}
