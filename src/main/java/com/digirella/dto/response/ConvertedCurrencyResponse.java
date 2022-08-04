package com.digirella.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConvertedCurrencyResponse {

    private String fromCurrency;
    private String toCurrency;
    private Double amount;
    private Double result;
    private String date;

}
