package com.purchasetransaction.service;

import com.purchasetransaction.client.FiscalDataTreasuryClient;
import com.purchasetransaction.config.FiscalDataTreasuryConfig;
import com.purchasetransaction.dto.CurrencyConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.MAX_PERIOD_IN_MONTHS;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.START_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stub.CurrencyConversionStub.buildCurrencyConversion;
import static stub.CurrencyConversionStub.buildCurrencyConversionWithTwoDecimals;
import static stub.ExchangeRateResponseStub.buildExchangeRateResponse;
import static stub.ExchangeRateResponseStub.buildExchangeRateThreeDecimalsResponse;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {

    @InjectMocks
    private CurrencyConverterService service;
    @Mock
    private FiscalDataTreasuryClient client;
    @Mock
    private FiscalDataTreasuryConfig config;

    @Test
    void getConvertedCurrency() {
        when(config.getRatesOfExchangesMaxPeriodInMonths())
                .thenReturn(MAX_PERIOD_IN_MONTHS);
        when(client.getExchangeRate(any(), any(), any()))
                .thenReturn(buildExchangeRateResponse());

        final CurrencyConversion currencyConversion = service
                .getConvertedCurrency(COUNTRY_CURRENCY, PURCHASE_DATE,
                        ORIGINAL_PURCHASE_AMOUNT);

        verify(client).getExchangeRate(COUNTRY_CURRENCY, START_DATE,
                PURCHASE_DATE);
        verify(config).getRatesOfExchangesMaxPeriodInMonths();
        assertEquals(buildCurrencyConversion(), currencyConversion);
    }

    @Test
    void getConvertedCurrency_whenValueHasMoreThanTwoDecimals_thenRoundToTwo() {
        when(config.getRatesOfExchangesMaxPeriodInMonths())
                .thenReturn(MAX_PERIOD_IN_MONTHS);
        when(client.getExchangeRate(any(), any(), any()))
                .thenReturn(buildExchangeRateThreeDecimalsResponse());

        final CurrencyConversion currencyConversion = service
                .getConvertedCurrency(COUNTRY_CURRENCY, PURCHASE_DATE,
                        ORIGINAL_PURCHASE_AMOUNT);

        assertEquals(buildCurrencyConversionWithTwoDecimals(),
                currencyConversion);
    }
}
