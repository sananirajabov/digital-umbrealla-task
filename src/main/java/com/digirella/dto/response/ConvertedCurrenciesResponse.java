package com.digirella.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConvertedCurrenciesResponse {

    private List<ConvertedCurrencyResponse> currencies;
}
