package com.digirella.service;

import com.digirella.dto.request.CurrencyRequest;
import com.digirella.dto.response.ConvertedCurrenciesResponse;
import com.digirella.dto.response.ConvertedCurrencyResponse;
import com.digirella.dto.response.CurrenciesResponse;

import javax.xml.bind.JAXBException;

/**
 *
 */

public interface CurrencyService {

    /**
     *
     */
    CurrenciesResponse saveCurrencies(CurrencyRequest currencyRequest) throws JAXBException;


    /**
     *
     */
    void deleteCurrenciesByGivenDate(String date);


    /**
     *
     */
    ConvertedCurrencyResponse convertAZNToTargetCurrencyByGivenDate(String amount,
                                                                    String currency,
                                                                    String date);


    /**
     *
     */
    ConvertedCurrenciesResponse convertAZNToAllCurrenciesByGivenDate(String amount,
                                                                     String date);


    /**
     *
     */
    ConvertedCurrenciesResponse convertAZNToTargetCurrencyByAllTime(String amount,
                                                                    String currency);
}
