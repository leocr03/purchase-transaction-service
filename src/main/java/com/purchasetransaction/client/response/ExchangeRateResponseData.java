package com.purchasetransaction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class ExchangeRateResponseData {

    @JsonProperty("country_currency_desc")
    String countryCurrencyDesc;
    @JsonProperty("exchange_rate")
    BigDecimal exchangeRate;
    @JsonProperty("record_date")
    LocalDate recordDate;
}
