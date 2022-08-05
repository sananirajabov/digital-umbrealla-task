package com.digirella.controller;

import com.digirella.dto.request.CurrencyRequest;
import com.digirella.dto.response.ConvertedCurrenciesResponse;
import com.digirella.dto.response.ConvertedCurrencyResponse;
import com.digirella.dto.response.CurrenciesResponse;
import com.digirella.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

/**
 *
 */

@Tag(name = "Currency Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    /**
     *
     */
    @Operation(description = "Save Currencies by Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currencies saved successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed: "
                    + "ENTITY_ALREADY_EXISTS, INVALID_DATE")
    })
    @PostMapping
    public ResponseEntity<CurrenciesResponse> saveCurrencies(@RequestBody CurrencyRequest currencyRequest) throws JAXBException {
        return ResponseEntity.ok(currencyService.saveCurrencies(currencyRequest));
    }

    /**
     *
     */
    @Operation(description = "Delete Currencies by Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Currencies deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed: INVALID_DATE")
    })
    @DeleteMapping("/{date}")
    public ResponseEntity<Void> deleteCurrenciesByDate(@PathVariable String date) {
        currencyService.deleteCurrenciesByGivenDate(date);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     */
    @Operation(description = "Convert AZN Currency To Target Currency by Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Converted successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed: "
                    + "INVALID_DATE, ENTITY_NOT_FOUND, INVALID_AMOUNT")
    })
    @GetMapping("/convert/{amount}/{currency}/{date}")
    public ResponseEntity<ConvertedCurrencyResponse> convertAznToTargetCurrencyByDate(@PathVariable String amount,
                                                                                      @PathVariable String currency,
                                                                                      @PathVariable String date) {

        return ResponseEntity.ok(currencyService.convertAZNToTargetCurrencyByGivenDate(amount, currency, date));
    }

    /**
     *
     */
    @Operation(description = "Convert AZN Currency To All Currencies by Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Converted successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed: "
                    + "INVALID_DATE, INVALID_AMOUNT")
    })
    @GetMapping("/convert/{amount}/{date}")
    public ResponseEntity<ConvertedCurrenciesResponse> convertAznToAllCurrenciesByDate(@PathVariable String amount,
                                                                                       @PathVariable String date) {

        return ResponseEntity.ok(currencyService.convertAZNToAllCurrenciesByGivenDate(amount, date));
    }

    /**
     *
     */
    @Operation(description = "Convert AZN Currency To Target Currency by Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Converted successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed: INVALID_AMOUNT")
    })
    @GetMapping("/convert/all/time/{amount}/{currency}")
    public ResponseEntity<ConvertedCurrenciesResponse> convertAznToTargetCurrencyByAllTime(@PathVariable String amount,
                                                                                           @PathVariable String currency) {

        return ResponseEntity.ok(currencyService.convertAZNToTargetCurrencyByAllTime(amount, currency));
    }


}
