package com.digirella.helper;

import com.digirella.exception.HttpHandledException;
import com.digirella.exception.error.ErrorCode;
import com.digirella.exception.error.ErrorResponse;
import com.digirella.model.Currency;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyServiceImplTestHelper {

    protected final static String NULL_DATE = null;
    protected final static String DATE = "03.08.2022";
    protected final static String EMPTY_DATE = "";
    protected final static String FUTURE_DATE = "12.12.2022";
    protected final static String AMOUNT = "100";
    protected final static String EMPTY_AMOUNT = "";
    protected final static String NEGATIVE_AMOUNT = "-100";
    protected final static String AMOUNT_IN_TEXT_FORMAT = "hundred";
    protected final static String CURRENCY_AZN = "AZN";
    protected final static String CURRENCY_USD = "USD";

    protected static List<Currency> populateMockCurrencies() {
        List<Currency> currencies = new ArrayList<>();

        Currency currencyUSD = Currency.builder()
                .id(1L)
                .name("1 ABŞ Dolları")
                .code("USD")
                .nominal(1)
                .value(1.7)
                .date("03.08.2022")
                .build();

        Currency currencyEUR = Currency.builder()
                .id(2L)
                .name("1 AVRO")
                .code("EUR")
                .nominal(1)
                .value(1.728)
                .date("03.08.2022")
                .build();

        Currency currencyAUD = Currency.builder()
                .id(2L)
                .name("1 Avstraliya dolları")
                .code("AUD")
                .nominal(1)
                .value(1.1831)
                .date("03.08.2022")
                .build();

        currencies.add(currencyUSD);
        currencies.add(currencyAUD);
        currencies.add(currencyEUR);

        return currencies;
    }

    protected static Currency populateMockCurrency() {
        return Currency.builder()
                .id(1L)
                .name("1 ABŞ Dolları")
                .code("USD")
                .nominal(1)
                .value(1.7)
                .date("03.08.2022")
                .build();
    }

    protected static List<Currency> populateMockUSDCurrenciesOnDifferentDates() {
        List<Currency> currencies = new ArrayList<>();

        Currency currencyOne = Currency.builder()
                .id(1L)
                .name("1 ABŞ Dolları")
                .code("USD")
                .nominal(1)
                .value(1.7)
                .date("01.08.2022")
                .build();

        Currency currencyTwo = Currency.builder()
                .id(2L)
                .name("1 ABŞ Dolları")
                .code("USD")
                .nominal(1)
                .value(1.65)
                .date("02.08.2022")
                .build();

        Currency currencyThree = Currency.builder()
                .id(3L)
                .name("1 ABŞ Dolları")
                .code("USD")
                .nominal(1)
                .value(1.9)
                .date("03.08.2022")
                .build();

        currencies.add(currencyOne);
        currencies.add(currencyTwo);
        currencies.add(currencyThree);

        return currencies;
    }

    public static void assertErrorCode(HttpHandledException exception, ErrorCode errorCode) {
        ErrorResponse errorResponse = exception.getErrorResponse();
        assertNotNull(errorResponse, "Error response is null");
        assertEquals(errorCode, errorResponse.getErrorCode(), "Invalid Error Code returned");
    }
}
