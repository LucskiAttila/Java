package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DescribeAccountCommandTest {

    @Test
    public void testOperateShouldReturnSignWhenNoUserSignedIn() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(null);

        DescribeAccountCommand underTest = new DescribeAccountCommand(userRepository);

        // When
        List<Object> result = underTest.operate();

        // Then
        assertEquals("sign", result.get(0));
        assertEquals(1, result.size());
        Mockito.verify(userRepository).findByIsSigned(true);
    }

    @Test
    public void testOperateShouldReturnAdminWhenAdminUserSignedIn() {
        // Given
        String username = "admin";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(true);
        BDDMockito.given(user.getUserName()).willReturn(username);

        DescribeAccountCommand underTest = new DescribeAccountCommand(userRepository);

        // When
        List<Object> result = underTest.operate();

        // Then
        assertEquals("admin", result.get(0));
        assertEquals(username, result.get(1));
        assertEquals(2, result.size());
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(user).getUserName();
    }

    @Test
    public void testOperateShouldReturnAdminWhenNotAdminUserSignedIn() {
        // Given
        String username = "admin";
        String CURRENCY = "HUF";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = Mockito.mock(User.class);

        BDDMockito.given(userRepository.findByIsSigned(true)).willReturn(user);
        BDDMockito.given(user.getIsAdmin()).willReturn(false);
        BDDMockito.given(user.getUserName()).willReturn(username);
        BDDMockito.given(user.getBook()).willReturn(Collections.emptyList());

        DescribeAccountCommand underTest = new DescribeAccountCommand(userRepository);
        underTest.setCurrency(CURRENCY);

        // When
        List<Object> result = underTest.operate();

        // Then
        assertEquals("user", result.get(0));
        assertEquals(username, result.get(1));
        assertEquals(Collections.emptyList(), result.get(2));
        assertEquals(CURRENCY, result.get(3));
        assertEquals(4, result.size());
        Mockito.verify(userRepository).findByIsSigned(true);
        Mockito.verify(user).getIsAdmin();
        Mockito.verify(user).getUserName();
        Mockito.verify(user).getBook();
    }
}
