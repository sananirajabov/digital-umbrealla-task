package com.digirella.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencyResponse {

    private String code;
    private String name;
    private Integer nominal;
    private Double value;
    private String date;
}
