package com.digirella.unit.service;

import com.digirella.dto.request.CurrencyRequest;
import com.digirella.dto.response.ConvertedCurrenciesResponse;
import com.digirella.dto.response.ConvertedCurrencyResponse;
import com.digirella.dto.response.CurrenciesResponse;
import com.digirella.dto.response.CurrencyResponse;
import com.digirella.exception.EntityAlreadyExistsException;
import com.digirella.exception.ValidationFailedException;
import com.digirella.helper.CurrencyServiceImplTestHelper;
import com.digirella.model.Currency;
import com.digirella.repository.CurrencyRepository;
import com.digirella.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;

import java.util.List;
import java.util.Optional;

import static com.digirella.exception.error.GeneralErrorCode.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest extends CurrencyServiceImplTestHelper {

    private CurrencyServiceImpl currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyService = new CurrencyServiceImpl(new RestTemplate(), currencyRepository);
    }

    @Test
    void saveCurrencies_providedValidDate_savedSuccessfully() throws JAXBException {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .date(DATE)
                .build();

        when(currencyRepository.saveAll(any())).thenReturn(populateMockCurrencies());
        CurrenciesResponse currenciesResponse = currencyService.saveCurrencies(currencyRequest);

        assertNotNull(currenciesResponse);
        assertEquals(currenciesResponse.getCurrencies().size(), populateMockCurrencies().size());

        for (int i = 0; i < populateMockCurrencies().size(); i++) {
            Currency expected = populateMockCurrencies().get(i);
            CurrencyResponse actual = currenciesResponse.getCurrencies().get(i);

            assertNotNull(expected.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getValue(), actual.getValue());
            assertEquals(expected.getNominal(), actual.getNominal());
            assertEquals(expected.getDate(), actual.getDate());
        }
    }

    @Test
    void saveCurrencies_providedValidDateButAlreadyExists_correspondingErrorThrown() {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .date(DATE)
                .build();

        when(currencyRepository.findAllByDate(any())).thenReturn(populateMockCurrencies());

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
                () -> currencyService.saveCurrencies(currencyRequest));

        assertErrorCode(exception, ENTITY_ALREADY_EXISTS);
    }

    @Test
    void saveCurrencies_dateNotProvided_correspondingErrorThrown() {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .date(NULL_DATE)
                .build();

        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.saveCurrencies(currencyRequest));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void saveCurrencies_invalidDateProvided_correspondingErrorThrown() {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .date(EMPTY_DATE)
                .build();

        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.saveCurrencies(currencyRequest));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void saveCurrencies_futureDateProvided_correspondingErrorThrown() {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .date(FUTURE_DATE)
                .build();

        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.saveCurrencies(currencyRequest));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void deleteCurrencies_providedValidDate_savedSuccessfully() {
        currencyService.deleteCurrenciesByGivenDate(DATE);
        verify(currencyRepository, times(1)).deleteAllByDate(DATE);
    }

    @Test
    void deleteCurrencies_invalidDateProvided_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.deleteCurrenciesByGivenDate(NULL_DATE));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void deleteCurrencies_futureDateProvided_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.deleteCurrenciesByGivenDate(FUTURE_DATE));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_providedValidParams_convertedSuccessfully() {
        when(currencyRepository.findByCodeAndDate(any(), any())).thenReturn(Optional.of(populateMockCurrency()));
        ConvertedCurrencyResponse response = currencyService.convertAZNToTargetCurrencyByGivenDate(AMOUNT, CURRENCY_USD, DATE);

        assertNotNull(response);
        assertEquals(CURRENCY_AZN, response.getFromCurrency());
        assertEquals(CURRENCY_USD, response.getToCurrency());
        assertEquals(Double.valueOf(AMOUNT), response.getAmount());
        assertEquals(DATE, response.getDate());
        assertEquals(58.82352941176471, response.getResult());
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_invalidDate_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByGivenDate(AMOUNT, CURRENCY_USD, NULL_DATE));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_notExistsDate_correspondingErrorThrown() {
        when(currencyRepository.findByCodeAndDate(any(), any())).thenReturn(Optional.empty());

        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByGivenDate(AMOUNT, CURRENCY_USD, DATE));

        assertErrorCode(exception, ENTITY_NOT_FOUND);
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_emptyAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByGivenDate(EMPTY_AMOUNT, CURRENCY_USD, DATE));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_invalidAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByGivenDate(NEGATIVE_AMOUNT, CURRENCY_USD, DATE));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_invalidStringAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByGivenDate(AMOUNT_IN_TEXT_FORMAT, CURRENCY_USD, DATE));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToTargetCurrencyByGivenDate_futureDateProvided_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByGivenDate(AMOUNT, CURRENCY_USD, FUTURE_DATE));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_providedValidParams_convertedSuccessfully() {
        when(currencyRepository.findAllByDate(any())).thenReturn(populateMockCurrencies());
        ConvertedCurrenciesResponse response = currencyService.convertAZNToAllCurrenciesByGivenDate(AMOUNT, DATE);
        List<ConvertedCurrencyResponse> currencies = response.getCurrencies();

        assertNotNull(response);
        assertEquals(response.getCurrencies().size(), populateMockCurrencies().size());

        for (int i = 0; i < populateMockCurrencies().size(); i++) {
            Currency expected = populateMockCurrencies().get(i);
            ConvertedCurrencyResponse actual = currencies.get(i);

            assertNotNull(expected.getId());
            assertEquals(CURRENCY_AZN, actual.getFromCurrency());
            assertEquals(expected.getCode(), actual.getToCurrency());
            assertEquals(parseDouble(AMOUNT), actual.getAmount());
            assertEquals(parseInt(AMOUNT) / expected.getValue(), actual.getResult());
            assertEquals(expected.getDate(), actual.getDate());
        }
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_notExistsByDate_convertedSuccessfully() {
        when(currencyRepository.findAllByDate(any())).thenReturn(List.of());
        ConvertedCurrenciesResponse response = currencyService.convertAZNToAllCurrenciesByGivenDate(AMOUNT, DATE);

        assertTrue(response.getCurrencies().isEmpty());
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_invalidDate_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToAllCurrenciesByGivenDate(AMOUNT, NULL_DATE));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_emptyAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToAllCurrenciesByGivenDate(EMPTY_AMOUNT, DATE));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_invalidAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToAllCurrenciesByGivenDate(NEGATIVE_AMOUNT, DATE));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_invalidStringAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToAllCurrenciesByGivenDate(AMOUNT_IN_TEXT_FORMAT, DATE));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToAllCurrenciesByGivenDate_futureDateProvided_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToAllCurrenciesByGivenDate(AMOUNT, FUTURE_DATE));

        assertErrorCode(exception, INVALID_REQUEST_BODY);
    }

    @Test
    void convertAZNToTargetCurrencyByAllTime_providedValidParams_convertedSuccessfully() {
        when(currencyRepository.findAllByCode(any())).thenReturn(populateMockUSDCurrenciesOnDifferentDates());
        ConvertedCurrenciesResponse response = currencyService.convertAZNToTargetCurrencyByAllTime(AMOUNT, CURRENCY_USD);
        List<ConvertedCurrencyResponse> currencies = response.getCurrencies();

        assertNotNull(response);
        assertEquals(response.getCurrencies().size(), populateMockCurrencies().size());

        for (int i = 0; i < populateMockUSDCurrenciesOnDifferentDates().size(); i++) {
            Currency expected = populateMockUSDCurrenciesOnDifferentDates().get(i);
            ConvertedCurrencyResponse actual = currencies.get(i);

            assertNotNull(expected.getId());
            assertEquals(CURRENCY_AZN, actual.getFromCurrency());
            assertEquals(expected.getCode(), actual.getToCurrency());
            assertEquals(parseDouble(AMOUNT), actual.getAmount());
            assertEquals(parseInt(AMOUNT) / expected.getValue(), actual.getResult());
            assertEquals(expected.getDate(), actual.getDate());
        }
    }

    @Test
    void convertAZNToTargetCurrencyByAllTime_emptyAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByAllTime(EMPTY_AMOUNT, CURRENCY_USD));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToTargetCurrencyByAllTime_invalidAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByAllTime(NEGATIVE_AMOUNT, CURRENCY_USD));

        assertErrorCode(exception, INVALID_AMOUNT);
    }

    @Test
    void convertAZNToTargetCurrencyByAllTime_invalidStringAmount_correspondingErrorThrown() {
        ValidationFailedException exception = assertThrows(ValidationFailedException.class,
                () -> currencyService.convertAZNToTargetCurrencyByAllTime(AMOUNT_IN_TEXT_FORMAT, CURRENCY_USD));

        assertErrorCode(exception, INVALID_AMOUNT);
    }
}
