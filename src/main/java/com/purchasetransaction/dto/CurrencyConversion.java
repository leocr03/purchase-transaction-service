package com.purchasetransaction.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class CurrencyConversion {

    String countryCurrency;
    LocalDate purchaseDate;
    BigDecimal exchangeRate;
    BigDecimal originalPurchaseAmount;
    BigDecimal convertedPurchaseAmount;
}
