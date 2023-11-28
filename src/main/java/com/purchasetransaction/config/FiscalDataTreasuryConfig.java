package com.purchasetransaction.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FiscalDataTreasuryConfig {

    @Value("${client.fiscal.data.treasury.url:https://api.fiscaldata.treasury.gov/services/api/fiscal_service}")
    private String fiscalServiceApiUrl;

    @Value("${client.fiscal.data.treasury.rates-of-exchanges.endpoint:/v1/accounting/od/rates_of_exchange}")
    private String ratesOfExchangesEndpoint;

    @Value("${client.fiscal.data.treasury.rates-of-exchanges.fixed-parameters:sort=-record_date&format=json&page[number]=1&page[size]=1&fields=country_currency_desc,exchange_rate,record_date}")
    private String ratesOfExchangesFixedParameters;

    @Value("${client.fiscal.data.treasury.rates-of-exchanges.query-date-period.in-months:6}")
    private Integer ratesOfExchangesMaxPeriodInMonths;
}
