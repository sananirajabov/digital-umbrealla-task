package com.digirella.controller;

import com.digirella.dto.request.CurrencyRequest;
import com.digirella.dto.response.ConvertedCurrenciesResponse;
import com.digirella.dto.response.ConvertedCurrencyResponse;
import com.digirella.dto.response.CurrenciesResponse;
import com.digirella.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

/**
 *
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    /**
     *
     */
    @PostMapping
    public ResponseEntity<CurrenciesResponse> saveCurrencies(@RequestBody CurrencyRequest currencyRequest) throws JAXBException {
        return ResponseEntity.ok(currencyService.saveCurrencies(currencyRequest));
    }

    /**
     *
     */
    @DeleteMapping("/{date}")
    public ResponseEntity<Void> deleteCurrenciesByDate(@PathVariable String date) {
        currencyService.deleteCurrenciesByGivenDate(date);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     */
    @GetMapping("/{amount}/{currency}/{date}")
    public ResponseEntity<ConvertedCurrencyResponse> convertAznToTargetCurrencyByDate(@PathVariable String amount,
                                                                                      @PathVariable String currency,
                                                                                      @PathVariable String date) {

        return ResponseEntity.ok(currencyService.convertAZNToTargetCurrencyByGivenDate(amount, currency, date));
    }

    /**
     *
     */
    @GetMapping("/{amount}/{date}")
    public ResponseEntity<ConvertedCurrenciesResponse> convertAznToAllCurrenciesByDate(@PathVariable String amount,
                                                                                       @PathVariable String date) {

        return ResponseEntity.ok(currencyService.convertAZNToAllCurrenciesByGivenDate(amount, date));
    }

    /**
     *
     */
    @GetMapping("/all/time/{amount}/{currency}")
    public ResponseEntity<ConvertedCurrenciesResponse> convertAznToTargetCurrencyByAllTime(@PathVariable String amount,
                                                                                           @PathVariable String currency) {

        return ResponseEntity.ok(currencyService.convertAZNToTargetCurrencyByAllTime(amount, currency));
    }


}
