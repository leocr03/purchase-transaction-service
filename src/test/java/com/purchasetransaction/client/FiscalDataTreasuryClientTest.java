package com.purchasetransaction.client;

import com.purchasetransaction.client.response.ExchangeRateResponse;
import com.purchasetransaction.config.FiscalDataTreasuryConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.START_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stub.ExchangeRateResponseStub.buildExchangeRateResponse;
import static stub.ExchangeRateResponseStub.buildUrl;

@ExtendWith(MockitoExtension.class)
class FiscalDataTreasuryClientTest {

    @InjectMocks
    private FiscalDataTreasuryClient client;
    @Mock
    private FiscalDataTreasuryConfig config;
    @Mock
    private RestTemplate restTemplate;

    @Test
    void getExchangeRate() {
        buildApiMocks();

        final ExchangeRateResponse response = client.getExchangeRate(
                COUNTRY_CURRENCY, START_DATE, PURCHASE_DATE);

        verify(restTemplate).getForObject(buildUrl(),
                ExchangeRateResponse.class);
        assertEquals(buildExchangeRateResponse(), response);
    }

    private void buildApiMocks() {
        final String apiUrl = "https://api.fiscaldata.treasury.gov/" +
                "services/api/fiscal_service";
        final String endpoint = "/v1/accounting/od/rates_of_exchange";
        final String fixedParameters = "sort=-record_date&format=json" +
                "&page[number]=1&page[size]=1&fields=country_currency_desc," +
                "exchange_rate,record_date";
        when(config.getFiscalServiceApiUrl()).thenReturn(apiUrl);
        when(config.getRatesOfExchangesEndpoint()).thenReturn(endpoint);
        when(config.getRatesOfExchangesFixedParameters())
                .thenReturn(fixedParameters);
        when(restTemplate.getForObject(buildUrl(), ExchangeRateResponse.class))
                .thenReturn(buildExchangeRateResponse());
    }
}
