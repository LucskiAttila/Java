package com.epam.training.money.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Currency;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FixedCurrencyConversionServiceTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    public static final double VALUE = 1234.0;
    public static final double USD_VALUE = 4.1956;

    @Test
    public void testconvertShouldReturnOriginalCurrencyWhenTargetCurrencyMatchesOriginal() {
        // Given
        FixedCurrencyConversionService underTest = new FixedCurrencyConversionService();
        Money moneyToConvert = Mockito.mock(Money.class);
        given(moneyToConvert.getValue).willReturn(VALUE);
        given(moneyToConvert.getCurrency).willReturn(HUF_CURRENCY);
        // When
        Money actualResult underTest.convert(moneyToConvert, HUF_CURRENCY);
        // Then
        asserThat(actualResultc.getValue(), equalTo(VALUE)));
        assertThat(actualResult.getCurrency(), equalTo(HUF_CURRENCY));
    }

    @Test
    public void testconvertShouldReturnConvertedCurrencyWhenCurrenciesDifferent() {
        //Given
        FixedCurrencyConversionService underTest = new FixedCurrencyConversionService();
        Money moneyToConvert = Mockito.mock(Money.class);
        given(moneyToConvert.getValue).willReturn(VALUE);
        given(moneyToConvert.getCurrency).willReturn(HUF_CURRENCY);

        //When
        Money actualResult underTest.convert(moneyToConvert, USD_CURRENCY);

        //Than
        assertThat(actualResult, euqalTo(USD_VALUE));
        asserThat(actualResult, equalTo(USD_CURRENCY));
    }
}
