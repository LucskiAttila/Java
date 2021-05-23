package com.epam.training.ticketservice.logic.handler.price.baseprice;

import com.epam.training.ticketservice.logic.command.price.baseprice.UpdateBasePriceCommand;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UpdateBasePriceCommandHandlerTest {

    String BASEPRICE = "10";

    @Test
    public void testUpdateBasePriceShouldReturnUserNotSignedInWhenSign() {
        // Given
        UpdateBasePriceCommand updateBasePriceCommand = Mockito.mock(UpdateBasePriceCommand.class);

        BDDMockito.given(updateBasePriceCommand.operate(BASEPRICE)).willReturn("sign");

        UpdateBasePriceCommandHandler underTest = new UpdateBasePriceCommandHandler(updateBasePriceCommand);

        // When
        String result = underTest.updateBasePrice(BASEPRICE);

        // Then
        assertEquals("You aren't signed in", result);
        Mockito.verify(updateBasePriceCommand).operate(BASEPRICE);
    }

    @Test
    public void testUpdateBasePriceShouldReturnUserNotSignedInAsAdminWhenAdmin() {
        // Given
        UpdateBasePriceCommand updateBasePriceCommand = Mockito.mock(UpdateBasePriceCommand.class);

        BDDMockito.given(updateBasePriceCommand.operate(BASEPRICE)).willReturn("admin");

        UpdateBasePriceCommandHandler underTest = new UpdateBasePriceCommandHandler(updateBasePriceCommand);

        // When
        String result = underTest.updateBasePrice(BASEPRICE);

        // Then
        assertEquals("You don't have permission", result);
        Mockito.verify(updateBasePriceCommand).operate(BASEPRICE);
    }

    @Test
    public void testUpdateBasePriceShouldReturnUserNotSignedInAsAdminWhenBadString() {
        // Given
        String BadString = "S";
        UpdateBasePriceCommand updateBasePriceCommand = Mockito.mock(UpdateBasePriceCommand.class);

        BDDMockito.given(updateBasePriceCommand.operate(BASEPRICE)).willReturn("S");

        UpdateBasePriceCommandHandler underTest = new UpdateBasePriceCommandHandler(updateBasePriceCommand);

        // When
        String result = underTest.updateBasePrice(BASEPRICE);

        // Then
        assertEquals("You add invalid integer " + BadString, result);
        Mockito.verify(updateBasePriceCommand).operate(BASEPRICE);
    }

    @Test
    public void testUpdateBasePriceShouldReturnUserNotSignedInAsAdminWhenSame() {
        // Given
        UpdateBasePriceCommand updateBasePriceCommand = Mockito.mock(UpdateBasePriceCommand.class);

        BDDMockito.given(updateBasePriceCommand.operate(BASEPRICE)).willReturn("same");

        UpdateBasePriceCommandHandler underTest = new UpdateBasePriceCommandHandler(updateBasePriceCommand);

        // When
        String result = underTest.updateBasePrice(BASEPRICE);

        // Then
        assertEquals("You add same base price " + BASEPRICE, result);
        Mockito.verify(updateBasePriceCommand).operate(BASEPRICE);
    }

    @Test
    public void testUpdateBasePriceShouldReturnBasePriceSuccessfullyUpdatedWhenOk() {
        // Given
        UpdateBasePriceCommand updateBasePriceCommand = Mockito.mock(UpdateBasePriceCommand.class);

        BDDMockito.given(updateBasePriceCommand.operate(BASEPRICE)).willReturn("ok");

        UpdateBasePriceCommandHandler underTest = new UpdateBasePriceCommandHandler(updateBasePriceCommand);

        // When
        String result = underTest.updateBasePrice(BASEPRICE);

        // Then
        assertEquals("Base price is successfully set to " + BASEPRICE, result);
        Mockito.verify(updateBasePriceCommand).operate(BASEPRICE);
    }
}
