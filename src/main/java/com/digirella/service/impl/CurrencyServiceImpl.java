package com.digirella.service.impl;

import com.digirella.dto.request.CurrencyRequest;
import com.digirella.dto.response.ConvertedCurrenciesResponse;
import com.digirella.dto.response.ConvertedCurrencyResponse;
import com.digirella.dto.response.CurrenciesResponse;
import com.digirella.dto.response.CurrencyResponse;
import com.digirella.exception.EntityAlreadyExistsException;
import com.digirella.exception.ValidationFailedException;
import com.digirella.model.Currency;
import com.digirella.repository.CurrencyRepository;
import com.digirella.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.digirella.mapper.CurrencyMapper.toCurrencies;
import static com.digirella.mapper.CurrencyMapper.toCurrenciesResponse;
import static com.digirella.util.ErrorResponseFactory.*;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;
    private final String CURRENCY_AZN_CODE = "AZN";

    @Override
    @Transactional
    public CurrenciesResponse saveCurrencies(CurrencyRequest currencyRequest) throws JAXBException {
        validateDate(currencyRequest.getDate());

        List<Currency> currencies = retrieveCurrenciesFromExternalAPI(currencyRequest.getDate());

        if (!currencyRepository.findAllByDate(currencyRequest.getDate()).isEmpty()) {
            throw new EntityAlreadyExistsException(buildEntityAlreadyExistsWithGivenDateError(currencyRequest.getDate()));
        }

        List<Currency> savedCurrencies = currencyRepository.saveAll(currencies);
        return toCurrenciesResponse(savedCurrencies, savedCurrencies.get(0).getDate());
    }

    @Override
    @Transactional
    public void deleteCurrenciesByGivenDate(String date) {
        validateDate(date);
        currencyRepository.deleteAllByDate(date);
    }

    @Override
    public ConvertedCurrencyResponse convertAZNToTargetCurrencyByGivenDate(String amount, String currency, String date) {
        validateDate(date);
        double numericAmount = validateAndGetAmount(amount);

        Optional<Currency> currencyByCodeAndDate = currencyRepository.findByCodeAndDate(currency, date);

        if (currencyByCodeAndDate.isEmpty()) {
            throw new ValidationFailedException(buildCurrencyOrDateNotFound(currency, date));
        }

        double result = numericAmount / currencyByCodeAndDate.get().getValue();

        return ConvertedCurrencyResponse.builder()
                .fromCurrency(CURRENCY_AZN_CODE)
                .amount(numericAmount)
                .toCurrency(currency)
                .result(result)
                .date(date)
                .build();
    }

    @Override
    public ConvertedCurrenciesResponse convertAZNToAllCurrenciesByGivenDate(String amount, String date) {
        validateDate(date);
        double numericAmount = validateAndGetAmount(amount);

        List<Currency> currenciesAllByDate = currencyRepository.findAllByDate(date);

        if (currenciesAllByDate.isEmpty()) {
            return ConvertedCurrenciesResponse.builder()
                    .currencies(List.of())
                    .build();
        }

        return getConvertedCurrenciesResponse(numericAmount, currenciesAllByDate);
    }

    @Override
    public ConvertedCurrenciesResponse convertAZNToTargetCurrencyByAllTime(String amount, String currency) {
        double numericAmount = validateAndGetAmount(amount);

        List<Currency> allCurrencies = currencyRepository.findAllByCode(currency);
        return getConvertedCurrenciesResponse(numericAmount, allCurrencies);
    }

    private ConvertedCurrenciesResponse getConvertedCurrenciesResponse(double numericAmount, List<Currency> currenciesAllByDate) {
        List<ConvertedCurrencyResponse> convertedCurrencyResponses = new ArrayList<>();

        currenciesAllByDate.forEach(currency ->
                convertedCurrencyResponses.add(ConvertedCurrencyResponse.builder()
                        .fromCurrency(CURRENCY_AZN_CODE)
                        .toCurrency(currency.getCode())
                        .amount(numericAmount)
                        .result(numericAmount / currency.getValue())
                        .date(currency.getDate())
                        .build()));

        return ConvertedCurrenciesResponse.builder()
                .currencies(convertedCurrencyResponses)
                .build();
    }

    private List<Currency> retrieveCurrenciesFromExternalAPI(String date) throws JAXBException {
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String EXTERNAL_CURRENCY_API_URL = "https://www.cbar.az/currencies/%s.xml";
        String url = format(EXTERNAL_CURRENCY_API_URL, date);

        ResponseEntity<String> retrievedCurrencies = restTemplate.getForEntity(url, String.class);

        JAXBContext context = JAXBContext.newInstance(CurrencyRequest.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CurrencyRequest unmarshalledCurrencies = (CurrencyRequest) unmarshaller
                .unmarshal(new StringReader(Objects.requireNonNull(retrievedCurrencies.getBody())));

        List<List<Currency>> currencies = new ArrayList<>();
        unmarshalledCurrencies.getValTypes()
                .forEach(valType -> currencies.add(toCurrencies(valType.getValutes(), date)));

        return Stream.concat(currencies.get(0).stream(), currencies.get(1).stream()).toList();
    }

    private void validateDate(String date) {
        if (date == null) {
            throw new ValidationFailedException(buildInvalidDateFormatError());
        }

        if (date.isEmpty()) {
            throw new ValidationFailedException(buildInvalidDateFormatError(date));
        }

        String[] split = date.split("\\.");
        int day = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);

        LocalDate requestedDate = LocalDate.of(year, month, day);

        if (requestedDate.isAfter(LocalDate.now())) {
            throw new ValidationFailedException(buildInvalidDateError(date));
        }

    }

    private double validateAndGetAmount(String amount) {
        if (amount.isEmpty()) {
            throw new ValidationFailedException(buildInvalidAmountError(amount));
        }

        double numericAmount;

        try {
            numericAmount = Double.parseDouble(amount);

            if (numericAmount <= 0.0D) {
                throw new ValidationFailedException(buildInvalidAmountError(amount));
            }

        } catch (NumberFormatException nfe) {
            throw new ValidationFailedException(buildInvalidAmountError());
        }

        return numericAmount;
    }
}
