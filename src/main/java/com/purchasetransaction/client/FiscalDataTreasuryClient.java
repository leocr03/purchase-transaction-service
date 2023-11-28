package com.purchasetransaction.client;

import com.purchasetransaction.client.response.ExchangeRateResponse;
import com.purchasetransaction.config.FiscalDataTreasuryConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class FiscalDataTreasuryClient {

    public static final String DYNAMIC_PARAMETERS =
            "&filter=country_currency_desc:eq:%s,record_date:gte:%s," +
            "record_date:lte:%s";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String URL_FORMAT = "%s%s?%s%s";

    private final FiscalDataTreasuryConfig config;
    private final RestTemplate restTemplate;

    public ExchangeRateResponse getExchangeRate(String countryCurrency,
                                                LocalDate startDate,
                                                LocalDate purchaseDate) {
        final String url = buildUrl(countryCurrency, startDate, purchaseDate);

        log.info("Getting exchange rate. countryCurrency={}, startDate={}, " +
                "purchaseDate={}, url={}", countryCurrency, startDate,
                purchaseDate, url);

        final ExchangeRateResponse response =
                restTemplate.getForObject(url, ExchangeRateResponse.class);

        log.info("Exchange rate obtained. response={}", response);
        return response;
    }

    private String buildUrl(String countryCurrency, LocalDate startDate,
                            LocalDate purchaseDate) {
        return URL_FORMAT.formatted(
                config.getFiscalServiceApiUrl(),
                config.getRatesOfExchangesEndpoint(),
                config.getRatesOfExchangesFixedParameters(),
                buildDynamicParameters(countryCurrency, startDate,
                        purchaseDate));
    }

    private String buildDynamicParameters(String countryCurrency,
                                          LocalDate startDate,
                                          LocalDate purchaseDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                DATE_FORMAT);
        return DYNAMIC_PARAMETERS.formatted(
                countryCurrency, formatter.format(startDate),
                formatter.format(purchaseDate));
    }
}
