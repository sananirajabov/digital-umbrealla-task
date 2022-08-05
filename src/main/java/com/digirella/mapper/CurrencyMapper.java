package com.digirella.mapper;

import com.digirella.dto.request.Valute;
import com.digirella.dto.response.CurrenciesResponse;
import com.digirella.dto.response.CurrencyResponse;
import com.digirella.model.Currency;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CurrencyMapper {

    public static Currency toCurrency(Valute valute, String date) {
        return Currency.builder()
                .code(valute.getCode())
                .name(valute.getName())
                .nominal(valute.getNominal())
                .value(valute.getValue())
                .date(date)
                .build();
    }

    public static List<Currency> toCurrencies(List<Valute> currencies, String date) {
        return currencies.stream()
                .map(currency -> toCurrency(currency, date))
                .toList();
    }

    public static CurrencyResponse toCurrencyResponse(Currency currency, String date) {
        return CurrencyResponse.builder()
                .code(currency.getCode())
                .name(currency.getName())
                .nominal(currency.getNominal())
                .value(currency.getValue())
                .date(date)
                .build();
    }

    public static CurrenciesResponse toCurrenciesResponse(List<Currency> currencies, String date) {
        List<CurrencyResponse> currencyResponses = currencies.stream()
                .map(cur -> toCurrencyResponse(cur, date))
                .toList();

        return CurrenciesResponse.builder()
                .currencies(currencyResponses)
                .build();
    }

}
