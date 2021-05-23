package com.epam.training.ticketservice.logic.command.price.baseprice;

import com.epam.training.ticketservice.database.entity.BasePrice;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.BasePriceRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UpdateBasePriceCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenUserNotSignedIn() {
        // Given
        String basPrice = "10";
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        UpdateBasePriceCommand underTest = new UpdateBasePriceCommand(basePriceRepository, userRepository);

        // When
        String result = underTest.operate(basPrice);

        // Then
        assertEquals("sign", result);
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenUserSignedInNotAdmin() {
        // Given
        String basPrice = "10";
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);

        UpdateBasePriceCommand underTest = new UpdateBasePriceCommand(basePriceRepository, userRepository);

        // When
        String result = underTest.operate(basPrice);

        // Then
        assertEquals("admin", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnBadStringWhenBasePriceIsInvalid() {
        // Given
        String basPrice = "1A";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);

        UpdateBasePriceCommand underTest = new UpdateBasePriceCommand(basePriceRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(basPrice);

        // Then
        assertEquals("A", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
    }

    @Test
    public void testOperateShouldReturnSameWhenBasPriceNotChange() {
        // Given
        String basPrice = "10";
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        BasePrice basePrice = Mockito.mock(BasePrice.class);
        List<BasePrice> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(basePriceRepository.findAll()).willReturn(list);
        BDDMockito.given(list.get(0)).willReturn(basePrice);
        BDDMockito.given(basePrice.getBasePriceValue()).willReturn(Integer.parseInt(basPrice));

        UpdateBasePriceCommand underTest = new UpdateBasePriceCommand(basePriceRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(basPrice);

        // Then
        assertEquals("same", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(basePriceRepository).findAll();
        Mockito.verify(list).get(0);
        Mockito.verify(basePrice).getBasePriceValue();
    }

    @Test
    public void testOperateShouldReturnOkWhenBasPriceSuccessfullyUpdate() {
        // Given
        String basPrice = "10";
        int newBasePrice = 100;
        List<Character> digits = List.of('0','1','2','3','4','5','6','7','8','9');
        BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User user = Mockito.mock(User.class);
        BasePrice basePrice = Mockito.mock(BasePrice.class);
        List<BasePrice> list = Mockito.mock(List.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(basePriceRepository.findAll()).willReturn(list);
        BDDMockito.given(list.get(0)).willReturn(basePrice);
        BDDMockito.given(basePrice.getBasePriceValue()).willReturn(newBasePrice);

        UpdateBasePriceCommand underTest = new UpdateBasePriceCommand(basePriceRepository, userRepository);
        underTest.setDigits(digits);

        // When
        String result = underTest.operate(basPrice);

        // Then
        assertEquals("ok", result);
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(basePriceRepository).findAll();
        Mockito.verify(list).get(0);
        Mockito.verify(basePrice).getBasePriceValue();
        Mockito.verify(basePriceRepository).delete(basePrice);
        Mockito.verify(basePriceRepository).save(new BasePrice(Integer.parseInt(basPrice)));
    }
}
