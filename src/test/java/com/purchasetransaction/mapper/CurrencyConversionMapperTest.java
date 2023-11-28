package com.purchasetransaction.mapper;

import com.purchasetransaction.client.response.ExchangeRateResponseData;
import com.purchasetransaction.dto.CurrencyConversion;
import org.junit.jupiter.api.Test;

import static constant.TestContants.CONVERTED_PURCHASE_AMOUNT;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static stub.CurrencyConversionStub.buildCurrencyConversion;
import static stub.ExchangeRateResponseStub.buildExchangeRateResponseData;

class CurrencyConversionMapperTest {

    @Test
    void mapToCurrencyConversion() {
        final ExchangeRateResponseData exchangeRateData =
                buildExchangeRateResponseData();

        final CurrencyConversion currencyConversion = CurrencyConversionMapper
                .mapToCurrencyConversion(PURCHASE_DATE,
                        ORIGINAL_PURCHASE_AMOUNT, exchangeRateData,
                        CONVERTED_PURCHASE_AMOUNT);

        assertEquals(buildCurrencyConversion(), currencyConversion);
    }
}
