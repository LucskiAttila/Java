package com.epam.training.ticketservice.logic.command.screening;

import com.epam.training.ticketservice.database.entity.Screening;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListScreeningsCommandTest {

    @Test
    public void testOperateShouldReturnScreeningListWhenCalling() {
        // Given
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);

        ListScreeningsCommand underTest = new ListScreeningsCommand(screeningRepository);

        BDDMockito.given(screeningRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Screening> result = underTest.operate();

        // Then
        assertEquals(Collections.emptyList(), result);
    }
}