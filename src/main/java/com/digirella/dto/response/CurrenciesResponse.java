package com.digirella.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrenciesResponse {

    private List<CurrencyResponse> currencies;
}
