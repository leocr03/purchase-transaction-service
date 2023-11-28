package com.purchasetransaction.mapper;

import com.purchasetransaction.client.response.ExchangeRateResponseData;
import com.purchasetransaction.dto.CurrencyConversion;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyConversionMapper {

    public static CurrencyConversion mapToCurrencyConversion(
            LocalDate purchaseDate, BigDecimal purchaseAmount,
            ExchangeRateResponseData rateResponseData,
            BigDecimal convertedAmount) {
        return CurrencyConversion.builder()
                .countryCurrency(rateResponseData.getCountryCurrencyDesc())
                .purchaseDate(purchaseDate)
                .exchangeRate(rateResponseData.getExchangeRate())
                .originalPurchaseAmount(purchaseAmount)
                .convertedPurchaseAmount(convertedAmount)
                .build();
    }
}
