package com.digirella.service;

import com.digirella.dto.request.CurrencyRequest;
import com.digirella.dto.response.ConvertedCurrenciesResponse;
import com.digirella.dto.response.ConvertedCurrencyResponse;
import com.digirella.dto.response.CurrenciesResponse;

import javax.xml.bind.JAXBException;

/**
 * Defines the basic set of Business-context operations on Currency business entity.
 */
public interface CurrencyService {

    /**
     * Save currencies with given parameters
     *
     * @param currencyRequest the details of currencies
     * @return retrieved currencies
     */
    CurrenciesResponse saveCurrencies(CurrencyRequest currencyRequest) throws JAXBException;


    /**
     * Delete currencies with given date
     *
     * @param date the date of currency
     */
    void deleteCurrenciesByGivenDate(String date);


    /**
     * Convert AZN currency to target currency with given parameters
     *
     * @param amount the amount of to be converted currency
     * @param currency to be converted currency from currency AZN
     * @param date the date of currency to be converted
     * @return converted currency
     */
    ConvertedCurrencyResponse convertAZNToTargetCurrencyByGivenDate(String amount,
                                                                    String currency,
                                                                    String date);


    /**
     * Convert AZN currency to all currencies with given parameters
     *
     * @param amount the amount of to be converted currency
     * @param date the date of currency to be converted
     * @return converted currencies
     */
    ConvertedCurrenciesResponse convertAZNToAllCurrenciesByGivenDate(String amount,
                                                                     String date);


    /**
     * Convert AZN currency to target currency by the all-time with given parameters
     *
     * @param amount the amount of to be converted currency
     * @param currency to be converted currency from currency AZN
     * @return converted currencies
     */
    ConvertedCurrenciesResponse convertAZNToTargetCurrencyByAllTime(String amount,
                                                                    String currency);
}
