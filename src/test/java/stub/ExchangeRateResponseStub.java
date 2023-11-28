package stub;

import com.purchasetransaction.client.response.ExchangeRateResponse;
import com.purchasetransaction.client.response.ExchangeRateResponseData;

import java.util.Collections;

import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.EXCHANGE_RATE;
import static constant.TestContants.EXCHANGE_RATE_FOUR_DECIMALS;
import static constant.TestContants.RECORD_DATE;

public class ExchangeRateResponseStub {

    public static ExchangeRateResponse buildExchangeRateResponse() {
        return ExchangeRateResponse.builder()
                .exchangeRateResponseDataList(
                        Collections.singletonList(
                                ExchangeRateResponseData.builder()
                                        .countryCurrencyDesc(COUNTRY_CURRENCY)
                                        .exchangeRate(EXCHANGE_RATE)
                                        .recordDate(RECORD_DATE)
                                        .build()))
                .build();
    }

    public static ExchangeRateResponse buildExchangeRateThreeDecimalsResponse() {
        return ExchangeRateResponse.builder()
                .exchangeRateResponseDataList(
                        Collections.singletonList(
                                ExchangeRateResponseData.builder()
                                        .countryCurrencyDesc(COUNTRY_CURRENCY)
                                        .exchangeRate(EXCHANGE_RATE_FOUR_DECIMALS)
                                        .recordDate(RECORD_DATE)
                                        .build()))
                .build();
    }

    public static ExchangeRateResponseData buildExchangeRateResponseData() {
        return ExchangeRateResponseData.builder()
                .countryCurrencyDesc(COUNTRY_CURRENCY)
                .exchangeRate(EXCHANGE_RATE)
                .recordDate(RECORD_DATE)
                .build();
    }

    public static String buildUrl() {
        return "https://api.fiscaldata.treasury.gov/services" +
                "/api/fiscal_service/v1/accounting/od/rates_of_exchange" +
                "?sort=-record_date&format=json&page[number]=1&page[size]=1" +
                "&fields=country_currency_desc,exchange_rate,record_date" +
                "&filter=country_currency_desc:eq:Brazil-Real," +
                "record_date:gte:2023-05-29,record_date:lte:2023-11-29";
    }
}
