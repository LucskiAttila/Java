package com.epam.training.ticketservice.logic.command.price.pricecomponent;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreatePriceComponentCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String name = "Name";
        String price = "10";
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        CreatePriceComponentCommand underTest = new CreatePriceComponentCommand(priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, price);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String name = "Name";
        String price = "10";
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        CreatePriceComponentCommand underTest = new CreatePriceComponentCommand(priceComponentRepository, userRepository);

        // When
        String result = underTest.operate(name, price);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnBadStringWhenPriceIsInvalid() {
        // Given
        String name = "Name";
        String price = "1A";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        CreatePriceComponentCommand underTest = new CreatePriceComponentCommand(priceComponentRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(name, price);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnExistWhenPriceComponentAlreadyExist() {
        // Given
        String name = "Name";
        String price = "10";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        PriceComponent priceComponent = Mockito.mock(PriceComponent.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(priceComponent);

        CreatePriceComponentCommand underTest = new CreatePriceComponentCommand(priceComponentRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(name, price);

        // Then
        assertEquals("exist", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(priceComponentRepository).findByName(name);
    }

    @Test
    public void testOperateShouldReturnOkWhenBasePriceCanBeCreate() {
        String name = "Name";
        String price = "10";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        PriceComponentRepository priceComponentRepository = Mockito.mock(PriceComponentRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(priceComponentRepository.findByName(name)).willReturn(null);

        CreatePriceComponentCommand underTest = new CreatePriceComponentCommand(priceComponentRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(name, price);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(priceComponentRepository).findByName(name);
        Mockito.verify(priceComponentRepository).save(new PriceComponent(name, Integer.parseInt(price)));
    }
}
