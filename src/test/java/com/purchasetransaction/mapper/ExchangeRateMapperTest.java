package com.purchasetransaction.mapper;

import com.purchasetransaction.client.response.ExchangeRateResponse;
import com.purchasetransaction.client.response.ExchangeRateResponseData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static constant.TestContants.EXCHANGE_RATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static stub.ExchangeRateResponseStub.buildExchangeRateResponse;
import static stub.ExchangeRateResponseStub.buildExchangeRateResponseData;

class ExchangeRateMapperTest {

    @Test
    void mapExchangeRateToValue() {
        final ExchangeRateResponse exchangeRate = buildExchangeRateResponse();

        final BigDecimal exchangeRateValue =
                ExchangeRateMapper.mapExchangeRateToValue(exchangeRate);

        assertEquals(EXCHANGE_RATE, exchangeRateValue);
    }

    @Test
    void mapExchangeRateToData() {
        final ExchangeRateResponse exchangeRate = buildExchangeRateResponse();

        final ExchangeRateResponseData exchangeRateData =
                ExchangeRateMapper.mapExchangeRateToData(exchangeRate);

        assertEquals(buildExchangeRateResponseData(), exchangeRateData);
    }
}