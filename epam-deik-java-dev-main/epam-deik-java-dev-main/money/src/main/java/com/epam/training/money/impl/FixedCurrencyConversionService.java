package com.epam.training.money.impl;

public class FixedCurrencyConversionService implements  CurrentConversionService{

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final String UNKNOWN_CURRENCY_ERROR_MESSAGE = "Unknown currency";

    private static final List<ConversionRate> CONVERSION_RATES = List.of(
            new ConversionRate(HUF_CURRENCY, USD_CURRENCY, 0.0034),
            new ConversionRate(USD_CURRENCY, HUF_CURRENCY, 249.3));

    @override
    public Money convert(Money moneyToConvert, Currency targetCurrency) {
        if(targetCurrency.equals(moneyToConvert.getCurrency())) {
            return moneyToConvert;
        }
        return CONVERSION_RATES.stream()
                .filter(currentConversionRate -> moneyToConvert.getCurrency().equals(currentConversionRate.source)
                        && targetCurrency.equals(currentConversionRate.target))
                .map(currentConversionRate -> new Money(currentConversationRate.convert(moneyToConvert.getValue()), targetCurrency))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(UNKNOWN_CURRENCY_ERROR_MESSAGE));
    }


    private static class ConversionRate {
        private final Currency source;
        private final Currency target;
        private final double rate;

        public ConversionRate(Currency source, Currency target, double rate) {
            this.source = source;
            this.target = target;
            this.rate = rate;
        }

        public double convert(double value) {
            return value * this.rate;
        }
    }
}
